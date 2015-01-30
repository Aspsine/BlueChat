package com.aspsine.bluechat.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.model.Device;
import com.aspsine.bluechat.ui.fragment.ListFragment;

import java.util.List;

/**
 * Created by littlexi on 2015/1/30.
 */
public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {

    private List<Device> mDevices;

    private ListFragment.OnRecyclerViewItemClickListener mListener;

    public DevicesAdapter(List<Device> devices) {

        mDevices = devices;
    }

    public void setOnRecyclerViewItemClickListener(ListFragment.OnRecyclerViewItemClickListener listener) {

        mListener = listener;
    }

    public ListFragment.OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {

        return mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_device, viewGroup, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Device device = mDevices.get(i);
        if (!TextUtils.isEmpty(device.name)) viewHolder.tvName.setText(device.name);
    }

    @Override
    public int getItemCount() {

        return mDevices == null ? 0 : mDevices.size();
    }

    public void remove(int position) {
        mDevices.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Device device, int position) {
        mDevices.add(device);
        notifyItemInserted(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivAvatar;
        public TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
        }
    }

    public static class DividerItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;

        public DividerItemDecoration(int color) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(color);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            for (int i = 0, size = parent.getChildCount(); i < size; i++) {
                View itemView = parent.getChildAt(i);
                c.drawLine(itemView.getLeft(), itemView.getBottom(), itemView.getRight(), itemView.getBottom(), mPaint);
            }

        }
    }
}
