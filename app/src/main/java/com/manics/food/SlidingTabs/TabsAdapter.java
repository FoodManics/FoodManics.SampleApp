package com.manics.food.SlidingTabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Yash on 8/30/2015.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private String[] titles={"Menu Highlight", "Lunch Menu", "Dinner Menu","Snacks","Beverages","Desserts"};

    public TabsAdapter(FragmentManager fm, List<Fragment> fragList){
        super(fm);
        this.fragmentList=fragList;
    }

    @Override
    public Fragment getItem(int position){

        Fragment frag=fragmentList.get(position);

        if(frag.getArguments()!= null){
            Bundle b=frag.getArguments();
            b.putInt("position", position);
            frag.setArguments(b);
        }
        //Log.d("inAdapterValue", ""+fragmentList.get(position).getArguments().getParcelableArrayList("QuickManuListFrag").size());
        return frag;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return (titles[position]);
    }


    @Override
    public int getCount(){
        return fragmentList.size();
    }


}
