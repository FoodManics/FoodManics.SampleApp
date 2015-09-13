package com.manics.food;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.manics.food.Common.MenuCommon;
import com.manics.food.Fragments.DinnerMenuPreview_Fragment;
import com.manics.food.Fragments.LunchMenuPreview_Fragment;
import com.manics.food.Fragments.QuickManuPreview_Fragment;
import com.manics.food.NavigationDrawer.NavigationDrawer_Frag;
import com.manics.food.SlidingTabs.SlidingTabLayout;
import com.manics.food.SlidingTabs.TabsAdapter;
import com.manics.food.foodmanics.R;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends MenuCommon {

    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);


        ViewPager mViewPager;
        SlidingTabLayout mTabs;
        TabsAdapter adapter;

        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        Log.d("Assigning Classvalue", "In HomePage1");
        List<Fragment> fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new QuickManuPreview_Fragment());
        fragmentList.add(new LunchMenuPreview_Fragment());
        fragmentList.add(new DinnerMenuPreview_Fragment());
        fragmentList.add(new QuickManuPreview_Fragment());
        fragmentList.add(new LunchMenuPreview_Fragment());
        fragmentList.add(new DinnerMenuPreview_Fragment());

        mViewPager= (ViewPager) findViewById(R.id.home_viewpager);
        adapter=new TabsAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);


        mTabs=(SlidingTabLayout)findViewById(R.id.tabs);
        mTabs.setViewPager(mViewPager);

        Log.d("Assigning Classvalue", "In HomePage");
        NavigationDrawer_Frag navDrawerFragment=(NavigationDrawer_Frag)getSupportFragmentManager().findFragmentById(R.id.navBar);
        navDrawerFragment.setUp(R.id.navBar,(DrawerLayout)findViewById(R.id.dlayout), toolbar);


        /*
        final ActionBar actBar=getSupportActionBar();
        actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        ActionBar.TabListener tabListener=new ActionBar.TabListener(){

            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft){
                mViewPager.setCurrentItem(tab.getPosition());
             }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft){
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft){


             }
        };


        actBar.addTab(actBar.newTab().setText("Main Menu").setTabListener(tabListener));
        actBar.addTab(actBar.newTab().setText("Page2").setTabListener(tabListener));
        actBar.addTab(actBar.newTab().setText("Page3").setTabListener(tabListener));

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                actBar.setSelectedNavigationItem(position);
            }
        });
*/


//        final ActionBar actBar=getActionBar();


    }



}

