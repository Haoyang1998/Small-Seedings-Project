package com.seuchild.smallseedling.lifetip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seuchild.smallseedling.R;

import java.util.List;

/**
 * LifeTip Fragment RecyclerView 适配器
 * Author: created by Ginger on 2018/9/4 22 20
 * E-Mail: 1020072294@qq.com
 */
public class LifeTipFragmentRecyclerViewAdapter extends RecyclerView.Adapter<LifeTipFragmentRecyclerViewAdapter.ViewHolder>{
    // 新闻列表
    private List<LifeTipItem> newsList;
    //Context
    private Context context;

    public LifeTipFragmentRecyclerViewAdapter(List<LifeTipItem> data, Context pcontext){
        this.context = pcontext;
        this.newsList = data;
        notifyDataSetChanged();
    }

    //  ViewHolder 对应recyclerview 中的一个 item
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        // Fragment中Recyclerview的单个item

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lifetip,parent,false);
        // 实例化 ViewHolder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    // 绑定数据类   完成原始数据的传入 到解析好数据的绑定
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        holder.title.setText(newsList.get(position).getTitle());
        holder.source.setText(newsList.get(position).getSource());
        holder.date.setText(newsList.get(position).getPublishTime());
        String imageurl = "http:"+newsList.get(position).getNewsImg();
        holder.imageurl.setText(imageurl);
        String newsId = newsList.get(position).getNewsId();

        holder.newsid.setText(newsId);
        //Glide库加载图片
        Glide.with(context)
                .load(imageurl)
                .into(holder.image);
    }

    @Override
    public int getItemCount(){
        return newsList == null ? 0:newsList.size();
    }

    // ViewHolder类
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView source;
        private TextView date;
        private TextView imageurl;
        private TextView newsid;
        private ImageView image;

        public ViewHolder(View itemView){
            super(itemView);
            //  获取itemView 中的组件
            title = itemView.findViewById(R.id.itemlifetip_title);
            source = itemView.findViewById(R.id.itemlifetip_source);
            date = itemView.findViewById(R.id.itemlifetip_time);
            image = itemView.findViewById(R.id.itemlifetip_imageview);
            imageurl = itemView.findViewById(R.id.itemlifetip_newsimageurl);
            newsid = itemView.findViewById(R.id.itemlifetip_newsid);
        }
    }
}
