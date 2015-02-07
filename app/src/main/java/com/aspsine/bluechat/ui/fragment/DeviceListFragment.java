package com.aspsine.bluechat.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.adapter.DevicesAdapter;
import com.aspsine.bluechat.listener.OnItemClickListener;
import com.aspsine.bluechat.listener.OnItemLongClickListener;
import com.aspsine.bluechat.model.Device;
import com.aspsine.bluechat.ui.activity.ChatActivity;
import com.aspsine.bluechat.ui.widget.DividerItemDecoration;
import com.aspsine.bluechat.service.BluetoothService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class DeviceListFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener, DeviceDialogFragment.OnPairDeviceListener {

    public static final String TAG = DeviceListFragment.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 0x100;

    public static final int MESSAGE_CONNECTED = 0x100;
    public static final int MESSAGE_READ = 0x200;
    public static final int MESSAGE_WRITE = 0x300;
    public static final int MESSAGE_TOAST = 0x400;

    public static final String EXTRA_DEVICE = "device_name";
    public static final String TOAST = "toast";

    private static final int DISCOVERABLE_DURATION = 500;

    private BluetoothService mBluetoothService;

    private List<Device> mDevices;

    private BluetoothAdapter mBluetoothAdapter;

    private DevicesAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private ProgressDialog progressDialog;

    public static DeviceListFragment newInstance() {
        DeviceListFragment fragment = new DeviceListFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeviceListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mDevices = new ArrayList<Device>();
        mAdapter = new DevicesAdapter(mDevices);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        progressDialog = new ProgressDialog(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            setupDevices();
            if (mBluetoothService == null) {
                mBluetoothService = new BluetoothService(mHandler);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getActivity(), "action_settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_searching) {
            Toast.makeText(getActivity(), "action_fresh", Toast.LENGTH_SHORT).show();
            showAvailableDevices();
            return true;
        }

        if (id == R.id.action_discoverable) {
            Toast.makeText(getActivity(), "action_fresh", Toast.LENGTH_SHORT).show();
            ensureDiscoverable();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (requestCode == Activity.RESULT_OK) {
                setupDevices();
            } else {
                Toast.makeText(getActivity(), "Please turn on the bluetooth and try again.", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onPair(Device device) {
        progressDialog.setTitle("Connecting To: " + device.getName());
        progressDialog.setMessage("Just a second...");
        progressDialog.show();

        connectDevice(device);
    }

    @Override
    public void onItemClick(int position, View view) {
        if (view.getId() == R.id.ivAvatar) {
            Toast.makeText(getActivity(), "id= " + position + " avatar onClick", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "id= " + position + " onClick", Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(getActivity(), ChatActivity.class));
    }

    @Override
    public void onItemLongClick(int position, View view) {

    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_CONNECTED:
                    String bluetoothDevice = msg.getData().getString(EXTRA_DEVICE);
                    Toast.makeText(getActivity(), "Connected to" + bluetoothDevice, Toast.LENGTH_SHORT).show();
//                    mAdapter.add(changeBluetoothDeviceToDevice(bluetoothDevice), mDevices.size());
                    progressDialog.dismiss();
                    break;

            }
        }
    };

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setupDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice pairedDevice : pairedDevices) {
                mDevices.add(changeBluetoothDeviceToDevice(pairedDevice));
            }
            mAdapter.notifyDataSetChanged();
        } else {
            showAvailableDevices();
            Toast.makeText(getActivity(), "No device have been paired!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
            startActivity(discoverableIntent);
        }
    }

    /**
     * show devices
     */
    private void showAvailableDevices() {
        FragmentTransaction ft = getActivity().getFragmentManager()
                .beginTransaction();
        DeviceDialogFragment.newInstance().show(ft, DeviceDialogFragment.TAG);
    }

    private void connectDevice(Device device) {
        String address = device.getAddress();
        BluetoothDevice btDevice = mBluetoothAdapter.getRemoteDevice(address);
        mBluetoothService.connect(btDevice);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private Device changeBluetoothDeviceToDevice(BluetoothDevice btDevice){
        Device device = new Device();
        device.setName(btDevice.getName() != null ? btDevice.getName() : "UNKNOWN BLUETOOTH DEVICE");
        device.setAddress(btDevice.getAddress());
        device.setId(String.valueOf(btDevice.getUuids()));
        return device;
    }

}
