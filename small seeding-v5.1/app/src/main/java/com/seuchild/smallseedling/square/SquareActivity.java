package com.seuchild.smallseedling.square;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.StartActivity;
import com.seuchild.smallseedling.myInfo.MyInfoActivity;
import com.seuchild.smallseedling.parent.ParentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareActivity extends AppCompatActivity{
    private List<Fragment> fragments=new ArrayList<>(2);
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String [] titles=new String[]{"动态","树洞"};
    private ImageView imageView;
    private BottomNavigationView bnv_square;
    private treeFragment m_treeFragment;
    private activitiesFragment m_activitiesFragment;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);

        bnv_square = findViewById(R.id.bnv_square);
        bnv_square.getMenu().getItem(1).setChecked(true);
        bnv_square.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    //  界面跳转方法 本界面刷新 新界面就新建一个
                    @Override
                    public boolean onNavigationItemSelected(@Nullable MenuItem item) {
                        int num = item.getOrder();
                        if( num ==1)
                        {
                            startActivity(new Intent(SquareActivity.this,StartActivity.class));
                        }
                        else if(num ==3){
                            startActivity(new Intent(SquareActivity.this,ParentActivity.class));
                        }
                        else if(num == 4){
                            startActivity(new Intent(SquareActivity.this,MyInfoActivity.class));
                        }
                        else
                            return false;
                        finish();
                        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
        );
        initView();
        initEvn();

    }


    private void initEvn() {
        tabLayout.addOnTabSelectedListener(listener);

    }
    private void initView(){
        tabLayout=findViewById(R.id.tl_square);
        viewPager=findViewById(R.id.vp_square);
        imageView=findViewById(R.id.iv_add);
        fragments.add(m_activitiesFragment.newInstance());
        fragments.add(m_treeFragment.newInstance());
        System.out.println("????????????????????????????????????");
        tabLayout.setupWithViewPager(viewPager);
       SquareFragmentAdapter adapter=new SquareFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(titles));
       viewPager.setAdapter(adapter);
    }
    private TabLayout.OnTabSelectedListener listener=new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            Log.e("TT","onTabSelected:"+tab.getText().toString());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            Log.e("TT","onTabUnselected"+tab.getText().toString());
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            Log.e("TT","onTabReselected" + tab.getText().toString());
        }
    };
    public void onClick_add(View view){
        finish();
        startActivity(new Intent(SquareActivity.this,add.class));
    }
    public void onBack(View view){
        finish();
    }
}


