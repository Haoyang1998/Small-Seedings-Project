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

import java.util.ArrayList;
import java.util.List;

public class SquareRecyclerViewAdpter extends RecyclerView.Adapter<SquareRecyclerViewAdpter.activityViewHolder>{
    private List<String> mcontent = new ArrayList<>();
    private List<String> mname = new ArrayList<>();
    private List<String> mimage  = new ArrayList<>();
    private Context mcontext;

    public SquareRecyclerViewAdpter(Context context,List<String> data,List<String> name,List<String> image){
        this.mcontent = data;
        this.mname=name;
        this.mimage=image;
        this.mcontext=context;
        notifyDataSetChanged();
    }
    @Override
    public activityViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.item_activities,null);
        activityViewHolder viewHolder = new activityViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull activityViewHolder holder, int position) {
            holder.mTv_content.setText(mcontent.get(position));
            holder.mTv_name.setText(mname.get(position));
            Glide.with(mcontext).load(mimage.get(position)).into(holder.mIv);
    }

    @Override
    public int getItemCount() {
        return mname == null ? 0:mname.size();
    }

    public static class activityViewHolder extends RecyclerView.ViewHolder{
        TextView mTv_name;
        TextView mTv_content;
        ImageView mIv;

        public activityViewHolder(View itemView){
            super(itemView);
            mTv_name= itemView.findViewById(R.id.acti_tv_name);
            mTv_content=itemView.findViewById(R.id.acti_tv_content);
            mIv=itemView.findViewById(R.id.acti_iv);
        }
    }
}