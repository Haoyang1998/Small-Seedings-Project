package com.seuchild.smallseedling.item;


import android.view.View;

public interface OnItemClickListener {

        void ItemClick(View v, int pos);
    /**
     * 删除按钮回调
     *
     * @param position
     */
    void onDeleteClick(View v,int position);

}
