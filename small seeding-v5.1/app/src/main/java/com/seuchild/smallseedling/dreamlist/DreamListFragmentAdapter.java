package com.seuchild.smallseedling.dreamlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class DreamListFragmentAdapter extends FragmentStatePagerAdapter{

    private List<Fragment> dreamlistFragments;
    private List<String> dreamlistTitles;

    public DreamListFragmentAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles){
        super(fm);
        dreamlistFragments = fragments;
        dreamlistTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return dreamlistFragments.get(position);
    }

    @Override
    public int getCount() {
        return dreamlistFragments==null?0:dreamlistFragments.size();
    }

    public Fragment getFragment(int position){
        return dreamlistFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return dreamlistTitles.get(position);
    }
}

