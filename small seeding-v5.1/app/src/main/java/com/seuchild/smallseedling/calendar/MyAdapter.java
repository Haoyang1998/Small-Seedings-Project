package com.seuchild.smallseedling.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.item.OnItemClickListener;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Notice> list1;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    public MyAdapter(List<Notice> list1, Context context) {
        this.list1 = list1;
        mInflater = LayoutInflater.from(context);
    }


    protected void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_calender_new_text_view,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MyViewHolder mholder = holder;
        mholder.tv3.setText(list1.get(position).getContent().toString());
    }
    public void removeItem(int position) {
        list1.remove(position);
        notifyDataSetChanged();
    }

}