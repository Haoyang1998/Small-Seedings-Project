package com.seuchild.smallseedling.lifetip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.seuchild.smallseedling.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 生活贴士功能
 * Author: created by Ginger on 2018/8/30 06 19
 * E-Mail: 1020072294@qq.com
 */
public class LifeTipActivity extends AppCompatActivity {
    public static final String TAG = "LifeTabActivity";
    public static final String[] titles = new String[]{"生活","校园","出行"};

    private TabLayout lifepage_tablayout;
    private ViewPager lifepage_viewpager;

    // ViewPager中LifeFragment的三个实例  如何获取?
    private LifeTipLifeFragment fragmentlife_tip;
    private LifeTipCampusFragment fragmentlife_school;
    private LifeTipOutFragment fragmentlife_walk;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifetip);
        initView();

    }

    public void initView(){
        lifepage_tablayout = findViewById(R.id.lifepage_tab);
        lifepage_viewpager = findViewById(R.id.lifepage_viewpager);

        // 给TabLayout增加选择监听器
       lifepage_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG,"onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       // 关联Tab布局和ViewPager
        lifepage_tablayout.setupWithViewPager(lifepage_viewpager);

        // 构造viewPager的lifeAdapter
        List<Fragment> fragments = new ArrayList<>();

        // 实例化fragment并将其添加到 viewPager中
        fragments.add(LifeTipLifeFragment.newInstance());
        fragments.add(LifeTipCampusFragment.newInstance());
        fragments.add(LifeTipOutFragment.newInstance());

        LifeTipFragmentAdapter adapter = new LifeTipFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(titles));
        lifepage_viewpager.setAdapter(adapter);
        lifepage_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){

            }

            @Override
            public void onPageSelected(int position){
                Log.i(TAG,"select page:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state){

            }
        });

    }
    //  返回上一个界面
    public void onBack(View view){
        finish();
    }

    // 设置点击事件
    public void playNews(View view){
        String newsId = "c5c146abfb62304d13a1b19eff0312ef";
        String newsTitle = "新闻标题";
        String newsImage = "https://inews.gtimg.com/newsapp_ls/0/4938414763_640330/0";

        // 获取lifetipitem 中适配器设置的参数
        newsId = ((TextView)view.findViewById(R.id.itemlifetip_newsid)).getText().toString();
        newsTitle = ((TextView)view.findViewById(R.id.itemlifetip_title)).getText().toString();
        newsImage = ((TextView)view.findViewById(R.id.itemlifetip_newsimageurl)).getText().toString();

        // 传递参数
        Intent intent=new Intent(LifeTipActivity.this,LifeTipItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("newsId",newsId);
        bundle.putString("newsTitle",newsTitle);
        bundle.putString("newsImage",newsImage);
        intent.putExtras(bundle);

        startActivity(intent);
    }

}
