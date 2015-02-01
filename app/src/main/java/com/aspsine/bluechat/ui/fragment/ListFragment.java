package com.aspsine.bluechat.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.adapter.DevicesAdapter;
import com.aspsine.bluechat.model.Device;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnRecyclerViewItemClickListener mListener;

    private static List<Device> mDevices = new ArrayList<Device>();

    private DevicesAdapter mAdapter;

    private RecyclerView mRecyclerView;

    static {
        mDevices.add(new Device("0", "Alex"));
        mDevices.add(new Device("1", "Bob"));
        mDevices.add(new Device("2", "Candy"));
        mDevices.add(new Device("3", "Demo"));
        mDevices.add(new Device("4", "Ella"));
        mDevices.add(new Device("5", "Frank"));
        mDevices.add(new Device("0", "Alex"));
        mDevices.add(new Device("1", "Bob"));
        mDevices.add(new Device("2", "Candy"));
        mDevices.add(new Device("3", "Demo"));
        mDevices.add(new Device("4", "Ella"));
        mDevices.add(new Device("5", "Frank"));
        mDevices.add(new Device("0", "Alex"));
        mDevices.add(new Device("1", "Bob"));
        mDevices.add(new Device("2", "Candy"));
        mDevices.add(new Device("3", "Demo"));
        mDevices.add(new Device("4", "Ella"));
        mDevices.add(new Device("5", "Frank"));
    }

    // TODO: Rename and change types of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAdapter = new DevicesAdapter(mDevices);
        mAdapter.setOnRecyclerViewItemClickListener(mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DevicesAdapter.DividerItemDecoration(getResources().getColor(R.color.style_divider)));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRecyclerViewItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRecyclerViewItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRecyclerViewItemClickListener {
        public void onRecyclerVieItemClick(int position);
    }
}
