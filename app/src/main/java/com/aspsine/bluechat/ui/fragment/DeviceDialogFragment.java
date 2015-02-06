package com.aspsine.bluechat.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.adapter.DevicesAdapter;
import com.aspsine.bluechat.listener.OnItemClickListener;
import com.aspsine.bluechat.model.Device;
import com.aspsine.bluechat.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/2/5.
 */
public class DeviceDialogFragment extends DialogFragment implements OnItemClickListener {
    public static final String TAG = DeviceDialogFragment.class.getSimpleName();
    private BluetoothAdapter mBluetoothAdapter;
    private DevicesAdapter mAdapter;
    private List<Device> mDevices;

    public static DeviceDialogFragment newInstance() {
        DeviceDialogFragment fragment = new DeviceDialogFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
        }

        mDevices = new ArrayList<Device>();
        mAdapter = new DevicesAdapter(mDevices);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_fragment_device);
        Window window = dialog.getWindow();
        RecyclerView recyclerView = (RecyclerView) window.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setTitle("Searching devices...");

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(mReceiver);
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                for (Device device : mDevices) {
                    if (device.getAddress().equals(bluetoothDevice.getAddress())) {
                        return;
                    }
                }
                if (bluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Device device = new Device();
                    device.setName(bluetoothDevice.getName());
                    device.setAddress(bluetoothDevice.getAddress());
                    device.setId(String.valueOf(bluetoothDevice.getUuids()));
                    mDevices.add(device);
                    mAdapter.notifyItemInserted(mDevices.size() - 1);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                ((ProgressBar)getDialog().getWindow().findViewById(R.id.progressBar)).setVisibility(View.GONE);
                if (mDevices.size() == 0) {
                    Toast.makeText(getActivity(), "no device found!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public void onItemClick(int position, View view) {

        Toast.makeText(getActivity(), mDevices.get(position).getAddress(), Toast.LENGTH_LONG).show();
    }
}
