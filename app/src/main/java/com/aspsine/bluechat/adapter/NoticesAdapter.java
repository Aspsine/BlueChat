package com.aspsine.bluechat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.model.Notice;
import com.aspsine.bluechat.util.DateUtils;

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
    public int getItemCount() {
        return mNotices == null ? 0 : mNotices.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mNotices.get(position).getType();
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
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Notice notice = mNotices.get(position);
        switch (viewType) {
            case Notice.TYPE_IN_COMING:
                InComingViewHolder inComingViewHolder = (InComingViewHolder) holder;
                inComingViewHolder.tvTime.setText(DateUtils.formatDate(notice.getTime()));
                inComingViewHolder.tvText.setText(notice.getMessage());
                break;

            case Notice.TYPE_RETURNING:
                ReturningViewHolder returningViewHolder = (ReturningViewHolder) holder;
                returningViewHolder.tvTime.setText(DateUtils.formatDate(notice.getTime()));
                returningViewHolder.tvText.setText(notice.getMessage());
                break;

            case Notice.TYPE_NEWS:
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
                break;

            case Notice.TYPE_SYSTEM:
                SystemViewHolder systemViewHolder = (SystemViewHolder) holder;
                break;

            default:
                break;
        }
    }


    private View inflate(int resource, ViewGroup parent) {

        return LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
    }

    public class InComingViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivAvatar;
        public TextView tvText;
        public TextView tvTime;

        public InComingViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    public class ReturningViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivAvatar;
        public TextView tvText;
        public TextView tvTime;

        public ReturningViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
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


}
