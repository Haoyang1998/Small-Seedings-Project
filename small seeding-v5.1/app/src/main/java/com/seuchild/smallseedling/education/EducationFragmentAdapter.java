package com.seuchild.smallseedling.education;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 教育 ViewPager适配器
 * Author: created by Ginger on 2018/9/1 23 40
 * E-Mail: 1020072294@qq.com
 */
public class EducationFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> educationFragments;
    private List<String> educationTitles;

    public EducationFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles){
        super(fm);
        educationFragments = fragments;
        educationTitles = titles;
    }

    // 返回第几个位置的 fragment
    @Override
    public Fragment getItem(int position){
        return educationFragments.get(position);
    }

   // 获取ViewPager实际绘制的列表项数量
    @Override
    public int getCount(){
        return educationFragments == null ?0:educationFragments.size();
    }

    public Fragment getFragment(int position){
        return educationFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return educationTitles.get(position);
    }
}
