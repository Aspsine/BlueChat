package com.aspsine.bluechat.ui.fragment;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.adapter.NoticesAdapter;
import com.aspsine.bluechat.model.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment
        implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = ChatFragment.class.getSimpleName();

    private NoticesAdapter mAdapter;

    private static final List<Notice> mNotices = new ArrayList<Notice>();

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
        }

        Notice notice = new Notice(Notice.TYPE_RETURNING, editable.toString());
        mNotices.add(notice);
        mAdapter.notifyItemInserted(mNotices.size());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
