package com.seuchild.smallseedling.lifetip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * 生活ViewPager适配器
 * Author: created by Ginger on 2018/9/2 11 37
 * E-Mail: 1020072294@qq.com
 */
public class LifeTipFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> lifeTipFragments;
    private List<String> lifeTitles;

    public LifeTipFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles){
        super(fm);
        lifeTipFragments = fragments;
        lifeTitles = titles;
    }

    @Override
    public Fragment getItem(int position){
        return lifeTipFragments.get(position);
    }

    @Override
    public int getCount(){
        return lifeTipFragments == null ?0: lifeTipFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return lifeTitles.get(position);
    }
}
