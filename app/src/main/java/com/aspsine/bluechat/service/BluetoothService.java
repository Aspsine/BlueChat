package com.aspsine.bluechat.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.aspsine.bluechat.ui.fragment.DeviceListFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Aspsine on 2015/2/5.
 */
public class BluetoothService {
    private static final String TAG = BluetoothService.class.getSimpleName();

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");


    private int mState;

    private BluetoothAdapter mAdapter;

    private AcceptThread mAcceptThread;

    private ConnectThread mConnectThread;

    private ConnectedThread mConnectedThread;

    private static Handler mHandler;

    private static BluetoothService mInstance;

    public static BluetoothService getInstance(Handler handler){
        if(mInstance == null){
            mInstance = new BluetoothService();
        }
        if(mHandler != handler){
            mHandler = handler;
        }
        return mInstance;
    }

    private BluetoothService() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
    }

    public synchronized int getState() {
        return mState;
    }

    private synchronized void setState(int state) {
        mState = state;
        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(DeviceListFragment.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    public synchronized void start() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_LISTEN);

        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
    }

    public synchronized void connect(BluetoothDevice device) {
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectThread = new ConnectThread(device);
        try {
            mConnectThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setState(STATE_CONNECTING);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mAcceptThread != null) {
            mAcceptThread.cancel();
        }

        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        Message msg = mHandler.obtainMessage(DeviceListFragment.MESSAGE_DEVICE);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DeviceListFragment.EXTRA_DEVICE, device);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        setState(STATE_CONNECTED);
    }

    private void connectionFailed() {
        Message msg = mHandler.obtainMessage(DeviceListFragment.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(DeviceListFragment.TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        // Start the service over to restart listening mode
        BluetoothService.this.start();
    }

    private void connectionLost() {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(DeviceListFragment.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(DeviceListFragment.TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        // Start the service over to restart listening mode
        BluetoothService.this.start();
    }

    public void write(byte[] out) {
        ConnectedThread r;
        synchronized (this) {
            if (mState != STATE_CONNECTED) {
                return;
            }
            r = mConnectedThread;
        }
        r.write(out);
    }


    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmpServerSocket = null;
            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
                    tmpServerSocket = mAdapter.listenUsingInsecureRfcommWithServiceRecord("BluetoothChatInSecure", MY_UUID_INSECURE);

                } else {
                    tmpServerSocket = mAdapter.listenUsingRfcommWithServiceRecord("BluetoothChatSecure", MY_UUID_SECURE);
                }

            } catch (IOException e) {
                Log.e(TAG, "accept failed:" + e.toString());
            }
            mmServerSocket = tmpServerSocket;
        }

        @Override
        public void run() {
            BluetoothSocket socket = null;

            while (mState != STATE_CONNECTED) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                if (socket != null) {
                    synchronized (BluetoothService.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                }

            }
        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            this.mmDevice = device;
            BluetoothSocket tmpSocket = null;
            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
                    tmpSocket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                } else {
                    tmpSocket = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmSocket = tmpSocket;
        }

        @Override
        public void run() {
            mAdapter.cancelDiscovery();
            try {
                mmSocket.connect();

            } catch (IOException e) {
                Log.e(TAG, "ConnectThread: " + e.toString());
                try {
                    mmSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                connectionFailed();
                return;
            }

            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }

            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInputStream;
        private final OutputStream mmOutpuStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInputStream = tmpIn;
            mmOutpuStream = tmpOut;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInputStream.read(buffer);
                    mHandler.obtainMessage(DeviceListFragment.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    connectionLost();
                    BluetoothService.this.start();
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                mmOutpuStream.write(buffer);
                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(DeviceListFragment.MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
