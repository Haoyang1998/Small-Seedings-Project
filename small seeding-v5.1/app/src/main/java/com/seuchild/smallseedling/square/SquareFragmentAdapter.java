package com.seuchild.smallseedling.square;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * create by cz
 */
public class SquareFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> squareTitles;

    public SquareFragmentAdapter(FragmentManager fm,List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments=fragments;
        this.squareTitles=titles;
    }

    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount(){
        return fragments == null ?0: fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return squareTitles.get(position);
    }

}
