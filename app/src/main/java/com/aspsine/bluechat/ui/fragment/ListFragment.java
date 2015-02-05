package com.aspsine.bluechat.ui.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ListFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener {

    public static final String TAG = ListFragment.class.getSimpleName();

    private List<Device> mDevices;

    private BluetoothAdapter mBluetoothAdapter;

    private DevicesAdapter mAdapter;

    private RecyclerView mRecyclerView;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
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

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice pairedDevice : pairedDevices) {
                Device device = new Device();
                device.setName(pairedDevice.getName());
                device.setAddress(pairedDevice.getAddress());
                device.setId(String.valueOf(pairedDevice.getUuids()));
                mDevices.add(device);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "no device have been paired!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_main, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getActivity(), "action_settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_fresh) {
            Toast.makeText(getActivity(), "action_fresh", Toast.LENGTH_SHORT).show();

            FragmentTransaction ft = getActivity().getFragmentManager()
                    .beginTransaction();
            DeviceDialogFragment.newInstance().show(ft, DeviceDialogFragment.TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
