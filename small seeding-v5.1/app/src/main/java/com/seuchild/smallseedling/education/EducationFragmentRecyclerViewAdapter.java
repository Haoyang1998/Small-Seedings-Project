package com.seuchild.smallseedling.education;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seuchild.smallseedling.R;

import java.util.ArrayList;
import java.util.List;

/**
 * EducationFragment 中 RecyclerView 适配器
 * Author: created by Ginger on 2018/9/2 23 33
 * E-Mail: 1020072294@qq.com
 */
public class EducationFragmentRecyclerViewAdapter extends RecyclerView.Adapter<EducationFragmentRecyclerViewAdapter.ViewHolder>{
    private List<Video> videos = new ArrayList<>();
    Context context;

    public EducationFragmentRecyclerViewAdapter(List<Video> data, Context context){
        this.videos = data;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        // 实例化展示的View RecyclerView 中的单个 Item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_education,parent,false);
        // 实例化 ViewHolder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // 绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        // 绑定数据
        //holder.mTv.setText(mData.get(position));
        holder.title.setText(videos.get(position).getVideoName());
        holder.time.setText(videos.get(position).getVideoTime());
        //System.out.println(videos.get(position).getVideoImageUrl()+"!!!!!!!!!!!!!!!!!!!!");
        holder.source.setText(videos.get(position).getVideoSource());
        // 设置图片和视频的url
        holder.imageurl.setText(videos.get(position).getVideoImageUrl());
        holder.videourl.setText(videos.get(position).getVideoUrl());
        Glide.with(context)
                .load(videos.get(position).getVideoImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount(){
        return videos == null ? 0:videos.size();
    }

    // ViewHolder类
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView time;
        TextView source;
        TextView imageurl;
        TextView videourl;
        ImageView image;

        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.itemeducation_title);
            time = itemView.findViewById(R.id.itemeducation_time);
            source = itemView.findViewById(R.id.education_source);
            imageurl = itemView.findViewById(R.id.itemeducation_imageurl);
            videourl = itemView.findViewById(R.id.itemeducation_videourl);
            image = itemView.findViewById(R.id.itemeducation_imageview);
        }
    }
}
