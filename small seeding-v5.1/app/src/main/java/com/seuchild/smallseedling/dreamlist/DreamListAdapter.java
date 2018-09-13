package com.seuchild.smallseedling.dreamlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuchild.smallseedling.R;

import java.util.ArrayList;
import java.util.List;


public class DreamListAdapter extends RecyclerView.Adapter<DreamListAdapter.ViewHolder> {

    private  List<String> mData ;
    private Integer image;

    public DreamListAdapter(List<String> data,Integer imageid){
        mData = data;
        image = imageid;
    }
    @Override
    public DreamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dreamlist,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(DreamListAdapter.ViewHolder holder, int position) {
        holder.mTv.setText(mData.get(position));
        holder.mIv.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0:mData.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;
        ImageView mIv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.itemdreamlist_title);
            mIv = itemView.findViewById(R.id.itemdreamlist_image);
        }
    }
}

