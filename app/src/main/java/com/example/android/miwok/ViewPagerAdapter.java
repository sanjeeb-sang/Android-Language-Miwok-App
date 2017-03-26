package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static android.R.attr.fragment;

/**
 * Created by Sanjeeb on 2/8/2017.
 */


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment fragment;
    private String mFragmentTitle;

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                fragment = new NumbersFragment();
                break;
            case 1:
                fragment = new FamilyMembersFragment();
                break;
            case 2:
                fragment = new ColorsFragment();
                break;
            case 3:
                fragment = new PhrasesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                mFragmentTitle = "Numbers";
                break;
            case 1:
                mFragmentTitle = "Family Members";
                break;
            case 2:
                mFragmentTitle = "Colors";
                break;
            case 3:
                mFragmentTitle = "Phrases";
                break;
        }
        return mFragmentTitle;
    }
}