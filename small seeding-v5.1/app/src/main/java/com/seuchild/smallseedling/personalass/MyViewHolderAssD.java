package com.seuchild.smallseedling.personalass;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuchild.smallseedling.R;

public class MyViewHolderAssD extends RecyclerView.ViewHolder {
    public TextView tv3;
    private TextView delete;

    public LinearLayout layout;


    public MyViewHolderAssD(View itemView) {
        super(itemView);
        tv3 =  itemView.findViewById(R.id.fragment_done_tv);
            delete = (TextView) itemView.findViewById(R.id.fragment_done_delete);
            layout = (LinearLayout) itemView.findViewById(R.id.fragment_done_item_layout);
    }
}
