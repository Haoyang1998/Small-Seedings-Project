package com.seuchild.smallseedling.square;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seuchild.smallseedling.R;

import java.util.List;

public class TreeRecyclerViewAdpter extends RecyclerView.Adapter<TreeRecyclerViewAdpter.treeViewHolder>{
    private List<String> mcontent;
    private List<String> mimage;
    private Context mcontext;

    public TreeRecyclerViewAdpter(Context context,List<String> data,List<String> image){
        this.mcontent = data;
        this.mimage=image;
        this.mcontext=context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public treeViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.item_tree,null);
        treeViewHolder viewHolder = new treeViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull treeViewHolder holder, int position) {
        holder.mTv_content.setText(mcontent.get(position));
        Glide.with(mcontext).load(mimage.get(position)).into(holder.mIv);
    }

    @Override
    public int getItemCount() {
        return mcontent.size();
    }

    public static class treeViewHolder extends RecyclerView.ViewHolder{
        TextView mTv_content;
        ImageView mIv;

        public treeViewHolder(View itemView){
            super(itemView);
            mTv_content=itemView.findViewById(R.id.tree_tv_content);
            mIv=itemView.findViewById(R.id.tree_iv);
        }
    }
}