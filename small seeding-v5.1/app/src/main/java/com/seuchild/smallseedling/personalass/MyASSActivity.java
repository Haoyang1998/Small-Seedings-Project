package com.seuchild.smallseedling.personalass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.seuchild.smallseedling.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyASSActivity extends AppCompatActivity  {

    private Toolbar mToolbar;

    private TabLayout tbl;
    private FragmentN fragment_not;
    private FragmentD fragment_done;
    public String[]titles = {"已解决","未解决"};
    private List<Fragment> fragmentList;
    private MyAdapterAss adapter;
    private LinearLayout container;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_ass_mine);

        container = findViewById(R.id.ass_linear);

        mToolbar = findViewById(R.id.personal_ass_mine_tb);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initfraglist();

        tbl = findViewById(R.id.fragment_tbl);

        tbl.addTab(tbl.newTab().setText(titles[0]));
        tbl.addTab(tbl.newTab().setText(titles[1]));

        tbl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                Fragment frag = (Fragment)adapter.instantiateItem(container,pos);
                adapter.setPrimaryItem(container,pos,frag);
                adapter.finishUpdate(container);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mine_ass_toolbar,menu);
        return true;
    }

    private void initfraglist(){
        fragmentList = new ArrayList<>();
        fragment_done = new FragmentD();
        fragment_not = new FragmentN();
        fragmentList.add(fragment_done);
        fragmentList.add(fragment_not);
        adapter = new MyAdapterAss(getSupportFragmentManager(),fragmentList,Arrays.asList(titles));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.personal_ass_mine:
                Intent i = new Intent();
                i.setClass(MyASSActivity.this,PersonalASSActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
