package com.aspsine.bluechat.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.adapter.NoticesAdapter;
import com.aspsine.bluechat.model.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    public static final String TAG = ChatFragment.class.getSimpleName();

    private NoticesAdapter mAdapter;

    private static List<Notice> mNotices = new ArrayList<Notice>();

    static {
        for (int i = 0; i<100; i++){
            mNotices.add(new Notice(i % 5, i + "hi"));
        }
    }

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(){
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return view;
    }


}
