package com.seuchild.smallseedling.dreamlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import com.seuchild.smallseedling.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: created by Ginger on 2018/9/3 14 28
 * E-Mail: 1020072294@qq.com
 */
public class DreamListActivity extends AppCompatActivity{
    private DreamListFragmentT true_fragment;
    private DreamListFragmentF false_fragment;

    private TabLayout dreamlistTab;
    private ViewPager dreamlistPager;
    private ImageView back,add;
    private List<String> Tdata = new ArrayList<>();
    private List<String> Fdata = new ArrayList<>();


    private List<EXAMPLE> exampleList = new ArrayList<>();

    public static final String[] titles = new String[]{"未实现","已实现"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreamlist);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DreamListActivity.this,DreamActivity.class);
                startActivity(in);
            }
        });

        initView();
    }


    private void initView() {
        dreamlistTab = findViewById(R.id.dreamlist_tab);
        dreamlistPager = findViewById(R.id.dreamlist_viewpager);
        //关联tab布局和viewPager
        dreamlistTab.setupWithViewPager(dreamlistPager);
        //构造viewpager适配器
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DreamListFragmentF.newInstance(Fdata));
        fragments.add(DreamListFragmentT.newInstance(Tdata));

        DreamListFragmentAdapter adapter = new DreamListFragmentAdapter(getSupportFragmentManager(),fragments,Arrays.asList(titles));
        dreamlistPager.setAdapter(adapter);
        dreamlistPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}


