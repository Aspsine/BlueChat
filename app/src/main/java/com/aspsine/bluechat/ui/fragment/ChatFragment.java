package com.aspsine.bluechat.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.adapter.NoticesAdapter;
import com.aspsine.bluechat.model.Notice;
import com.aspsine.bluechat.service.BluetoothService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment
        implements View.OnClickListener {

    public static final String TAG = ChatFragment.class.getSimpleName();

    private List<Notice> mNotices;

    private BluetoothAdapter mBluetoothAdapter;

    private NoticesAdapter mAdapter;

    private EditText etEditor;

    private Button btnSend;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        BluetoothService.getInstance(mHandler);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
        }

        mNotices = new ArrayList<Notice>();
        mAdapter = new NoticesAdapter(mNotices);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvChat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);

        etEditor = (EditText) view.findViewById(R.id.etEditor);

        btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Editable editable = etEditor.getText();
        if (editable != null) {
            Toast.makeText(getActivity(), "Content: " + editable.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
            return;
        }

        BluetoothService.getInstance(mHandler).write(editable.toString().getBytes());
        etEditor.setText("");
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DeviceListFragment.MESSAGE_READ:
                    addTime();
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Notice read = new Notice();
                    read.setType(Notice.TYPE_IN_COMING);
                    read.setTime(new Date());
                    read.setMessage(readMessage);
                    mNotices.add(read);
                    mAdapter.notifyItemInserted(mNotices.size());
                    break;
                case DeviceListFragment.MESSAGE_WRITE:
                    addTime();
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Notice write = new Notice();
                    write.setType(Notice.TYPE_RETURNING);
                    write.setTime(new Date());
                    write.setMessage(writeMessage);
                    mNotices.add(write);
                    mAdapter.notifyItemInserted(mNotices.size());
                    break;
                default:
                    Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    void addTime() {
        if(mNotices.size()%4 == 0){
            Notice time = new Notice();
            time.setType(Notice.TYPE_TIME);
            time.setTime(new Date());
            mNotices.add(time);
            mAdapter.notifyItemInserted(mNotices.size());
        }
    }
}
