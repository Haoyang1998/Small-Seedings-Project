package com.seuchild.smallseedling.personalass;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


public class MyAdapterAss extends FragmentStatePagerAdapter {
    private List<Fragment> Frag;
    private List<String> titles;
    private int img[];

    public MyAdapterAss(FragmentManager fm, List<Fragment> fragments, List<String> titles){
        super(fm);
        Frag = fragments;
        this.titles = titles;
    }

    // 返回第几个位置的 fragment
    @Override
    public Fragment getItem(int position){
        return Frag.get(position);
    }

    // 获取ViewPager实际绘制的列表项数量
    @Override
    public int getCount(){
        return Frag == null ?0:Frag.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles.get(position);
    }

}