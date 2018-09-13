package com.seuchild.smallseedling.myInfo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuchild.smallseedling.R;


/**
 * 个人信息界面用户条
 * Author: created by Ginger on 2018/8/28 15 53
 * E-Mail: 1020072294@qq.com
 */
public class item_infobar_view extends LinearLayout{
    private ImageView imageView; //myinfo_item图标
    private TextView textView;  //myinfo_item文字
    private ImageView bottomView;   //下划线
    private boolean isbottom=true;   //是否显示下划线\

    // 构造函数
    public item_infobar_view(Context context) {
        this(context,null);
    }

    public item_infobar_view(Context context, @Nullable AttributeSet attrs){
        this(context,attrs,-1);
    }


    public item_infobar_view(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.item_infobar,this);
        //  获取自定义控件的属性
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.item_infobar_view);
        isbottom=ta.getBoolean(R.styleable.item_infobar_view_show_bottomline,true);
        bottomView=findViewById(R.id.item_bottom);
        imageView=findViewById(R.id.item_img);
        textView=findViewById(R.id.item_text);

        //获取属性值并设置
        textView.setText(ta.getString(R.styleable.item_infobar_view_show_text));
        imageView.setBackgroundResource(ta.getResourceId(R.styleable.item_infobar_view_show_leftimg,R.drawable.ic_myinfo_settings));

        ta.recycle();
        initview();

    }

    private void initview(){
        if(isbottom){
            bottomView.setVisibility(View.VISIBLE);
        }else{
            bottomView.setVisibility(View.GONE);
        }
    }
}
