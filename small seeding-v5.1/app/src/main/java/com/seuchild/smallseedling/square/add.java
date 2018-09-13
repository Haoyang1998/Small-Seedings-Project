package com.seuchild.smallseedling.square;

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

public class add extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String [] str=new String[]{"动态","树洞"};
    private addActiFragment m_addActiFragment;
    private addTreeFragment m_addTreeFragment;
    private List<Fragment> fragments=new ArrayList<>(2);

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_add);
        tv=findViewById(R.id.square_bu_cancel);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        initEvn();
    }
    private void initView(){
        tabLayout=findViewById(R.id.tl_square_add);
        viewPager=findViewById(R.id.square_vp_add);
        fragments.add(m_addActiFragment.newInstance());
        fragments.add(m_addTreeFragment.newInstance());
        tabLayout.setupWithViewPager(viewPager);
        SquareFragmentAdapter adapter=new SquareFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(str));
        viewPager.setAdapter(adapter);
    }
    private void initEvn() {
        tabLayout.addOnTabSelectedListener(listener);
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


}
