package com.manics.food.NavigationDrawer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.manics.food.ItemSelection;
import com.manics.food.ListView.ListItem;
import com.manics.food.foodmanics.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer_Frag extends android.support.v4.app.Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrwaer;
    private boolean mFromSavedInstanceState;
    private View navigationBar;
    public static final String PREF_FILE_NAME = "testPref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;
    private NavigationBarRecyclerViewAdapter navBarRecycleViewAdapt;

    public NavigationDrawer_Frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Assigning Classvalue", "OnCreateStarted");
        mUserLearnedDrwaer = (Boolean.getBoolean(readFromPreference(getActivity(), KEY_USER_LEARNED_DRAWER, "false")));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
        Log.d("Assigning Classvalue", "OnCreateFinished");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        navBarRecycleViewAdapt = new NavigationBarRecyclerViewAdapter(getActivity(), createObjectList());
        recyclerView.setAdapter(navBarRecycleViewAdapt);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
                Log.d("DebugLogs", "onInterceptTouchEvent");
                if(child!=null){

                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                Log.d("DebugLogs", "onTouchEvent");
            }
        });

        return layout;
    }


    public static List<NavigationBarRawData> createObjectList() {
        List<NavigationBarRawData> rowDataList = new ArrayList<>();
        int[] icons = {R.drawable.signin_signup_link, R.drawable.menu_icon, R.drawable.payment_icon, R.drawable.faq_icon,R.drawable.signin_signup_link, R.drawable.menu_icon, R.drawable.payment_icon, R.drawable.faq_icon};
        String[] navTitles = {"SignIn/SignUp User", "Menu", "Payment Options", "FAQ","SignIn/SignUp User", "Menu", "Payment Options", "FAQ"};

        Log.d("Assigning Classvalue", "In onCreateView ");
        for (int i = 0; i < navTitles.length && i < icons.length; i++) {
            NavigationBarRawData current = new NavigationBarRawData();
            current.setNavTitle(navTitles[i]);
            current.setImageID(icons[i]);
            rowDataList.add(current);
        }
        return rowDataList;
    }


    public void setUp(int navigationBarID, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        navigationBar = getActivity().findViewById(navigationBarID);
        Log.d("Assigning Classvalue", "SetupStarted");

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrwaer) {
                    mUserLearnedDrwaer = true;
                    saveToSharedPreference(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrwaer + "");
                }
                Log.d("DebugLogs", "DrawerOpened");
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
                Log.d("DebugLogs", "DrawerClosed");
            }

           @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);


                if(!isDrawerIndicatorEnabled()){
                    Log.d("Debug Logs", "IndicatorDEnabled!!!");
                }
                else{
                    Log.d("Debug Logs", "IndicatorDisabled!!!");
                }

                Log.d("Debug Logs", "StateChanged!!!");
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset < 0.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }
        };

        Log.d("Assigning Classvalue", "SetupMiddle");

        if (!mUserLearnedDrwaer && !mFromSavedInstanceState) {
            //mDrawerLayout.openDrawer(navigationBar);
        }

        //Makes NavDrawer strip available, and listens on clicks
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        Log.d("Assigning Classvalue", "SetupClosed");
    }


    public static void saveToSharedPreference(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferenceObj = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferenceObj.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreference(Context context, String preferenceName, String defaultValue) {

        SharedPreferences sharedPreferenceObj = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor=sharedPreferenceObj.edit();
        return sharedPreferenceObj.getString(preferenceName, defaultValue);
    }

}
