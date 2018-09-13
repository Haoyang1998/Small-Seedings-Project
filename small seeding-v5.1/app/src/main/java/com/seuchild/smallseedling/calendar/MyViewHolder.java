package com.seuchild.smallseedling.calendar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuchild.smallseedling.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tv3;
    public TextView delete;
    public LinearLayout layout;


    public MyViewHolder(View itemView) {
        super(itemView);
        tv3 =  itemView.findViewById(R.id.but3);
        delete = (TextView) itemView.findViewById(R.id.item_delete);
        layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
    }
}
