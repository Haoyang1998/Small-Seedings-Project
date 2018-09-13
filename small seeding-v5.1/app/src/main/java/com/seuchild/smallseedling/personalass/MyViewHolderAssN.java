package com.seuchild.smallseedling.personalass;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuchild.smallseedling.R;

public class MyViewHolderAssN extends RecyclerView.ViewHolder {
    public TextView tv3;
    private TextView finish;

    public LinearLayout layout;


    public MyViewHolderAssN(View itemView) {
        super(itemView);
        tv3 =  itemView.findViewById(R.id.fragment_not_tv);
            finish= (TextView) itemView.findViewById(R.id.fragment_not_finish);
            layout = (LinearLayout) itemView.findViewById(R.id.fragment_not_item_layout);
    }
}
