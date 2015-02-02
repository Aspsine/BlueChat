package com.aspsine.bluechat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.model.Notice;

import java.util.List;

/**
 * Created by sf on 2015/2/2.
 */
public class NoticesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Notice> mNotices;

    public NoticesAdapter(List<Notice> notices) {
        mNotices = notices;
    }

    @Override
    public int getItemViewType(int position) {
        return mNotices.get(position).type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;

        switch (viewType) {
            case Notice.TYPE_IN_COMING:
                itemView = inflate(R.layout.item_notice_incoming, parent);
                return new InComingViewHolder(itemView);

            case Notice.TYPE_RETURNING:
                itemView = inflate(R.layout.item_notice_returning, parent);
                return new ReturningViewHolder(itemView);

            case Notice.TYPE_NEWS:
                itemView = inflate(R.layout.item_notice_news, parent);
                return new NewsViewHolder(itemView);

            case Notice.TYPE_SYSTEM:
                itemView = inflate(R.layout.item_notice_system, parent);
                return new SystemViewHolder(itemView);

            case Notice.TYPE_TIME:
                itemView = inflate(R.layout.item_notice_time, parent);
                return new TimeViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mNotices == null ? 0 : mNotices.size();
    }

    private View inflate(int resource, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
    }

    public class InComingViewHolder extends RecyclerView.ViewHolder {

        public InComingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ReturningViewHolder extends RecyclerView.ViewHolder {
        public ReturningViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public NewsViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class SystemViewHolder extends RecyclerView.ViewHolder {

        public SystemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {

        public TimeViewHolder(View itemView) {
            super(itemView);
        }
    }

}
