package com.seuchild.smallseedling.education;

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
 * 教育平台功能
 * Author: created by Ginger on 2018/8/30 06 19
 * E-Mail: 1020072294@qq.com
 */
public class EducationActivity extends AppCompatActivity {
    public static final String TAG = "EducationTabActivity";
    public static final String[] titles = new String[]{"关注","推荐","我的"};

    private TabLayout educationpage_tablayout;
    private ViewPager educationpage_viewPager;

    // ViewPager中对应的三个Fragment
    private EducationFocusFragment fragment_focus;
    private EducationRecommendFragment fragment_recommend;
    private EducationMyFragment fragment_my;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_education);
        //  关联TabLayout与ViewPager 并为ViewPager设置自定义适配器
        initView();
    }

    // 初始化布局 viewPager适配器 viewPager与tab绑定
    private void initView(){
        educationpage_tablayout = (TabLayout)findViewById(R.id.educationpage_tab);
        educationpage_viewPager = (ViewPager)findViewById(R.id.educationpage_viewpager);

        // 给TabLayout增加选择监听器
        educationpage_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        // 关联tab布局和viewPager
        educationpage_tablayout.setupWithViewPager(educationpage_viewPager);

        // 构造viewPager适配器
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(EducationFocusFragment.newInstance());
        fragments.add(EducationRecommendFragment.newInstance());
        fragments.add(EducationMyFragment.newInstance());

        EducationFragmentAdapter adapter = new EducationFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(titles));
        educationpage_viewPager.setAdapter(adapter);
        educationpage_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
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

    // 返回 单击调用函数
    public void onBack(View view){
        finish();
    }

    // 单击播放视频
    public void playVideo(View view){
        String video_url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        String video_title = "测试视频";
        String video_image = "http://39.105.38.48/images/2018/09/12/eyes.gif";

        // 获取item education 中适配器设置的参数
        video_url = ((TextView)view.findViewById(R.id.itemeducation_videourl)).getText().toString();
        video_title = ((TextView)view.findViewById(R.id.itemeducation_title)).getText().toString();
        video_image = ((TextView)view.findViewById(R.id.itemeducation_imageurl)).getText().toString();
        // 传递参数
        Intent intent=new Intent(EducationActivity.this,EducationVideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("video_url",video_url);
        bundle.putString("video_title",video_title);
        bundle.putString("video_image",video_image);
        intent.putExtras(bundle);

        startActivity(intent);
    }

}
