package com.seuchild.smallseedling.personalass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.seuchild.smallseedling.item.OnItemClickListener;


public class AssFragmentPageAdapterD extends RecyclerView.Adapter<MyViewHolderAssD> {

    private List<Helper> list1;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;
    private MyViewHolderAssD holder;
    private int v;

    public AssFragmentPageAdapterD(List<Helper> list1, Context context, int i) {
        this.list1 = list1;
        mInflater = LayoutInflater.from(context);
        v  = i;
    }


    protected void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolderAssD onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(v,parent,false);
        holder = new MyViewHolderAssD(view);
        return holder;
    }


    @Override
    public int getItemCount() {
        return list1.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolderAssD holder, int position) {
        holder.tv3.setText(list1.get(position).getTitle()+"\n"
        +list1.get(position).getContent());
    }
    public void removeItem(int position) {
        list1.remove(position);
        notifyDataSetChanged();
    }

}