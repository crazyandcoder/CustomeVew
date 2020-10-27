package com.crazyandcoder.top.customeview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyandcoder.top.customeview.R;
import com.crazyandcoder.top.customeview.bean.ViewItemBean;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.CustomeViewHolder> {

    private List<ViewItemBean> data;
    private OnItemClickListener listener;

    public ViewAdapter(List<ViewItemBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CustomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_item_jetpack, parent, false);
        return new CustomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeViewHolder holder, final int position) {
        holder.itemTv.setText("" + data.get(position).getName());
        holder.itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position, data.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomeViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTv;

        public CustomeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTv = itemView.findViewById(R.id.itemView);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int position, ViewItemBean item);
    }
}
