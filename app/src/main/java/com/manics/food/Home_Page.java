package com.manics.food;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manics.food.Common.MenuCommon;
import com.manics.food.Fragments.DinnerMenuPreview_Fragment;
import com.manics.food.Fragments.LunchMenuPreview_Fragment;
import com.manics.food.Fragments.QuickManuPreview_Fragment;
import com.manics.food.HttpAndRest.HttpManager;
import com.manics.food.ListView.CuisineList;
import com.manics.food.ListView.ListItem;
import com.manics.food.NavigationDrawer.NavigationDrawer_Frag;
import com.manics.food.SlidingTabs.SlidingTabLayout;
import com.manics.food.SlidingTabs.TabsAdapter;
import com.manics.food.foodmanics.AppLoading;
import com.manics.food.foodmanics.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Home_Page extends MenuCommon {

    public static Toolbar toolbar;
    private ArrayList<ListItem> masterList;
    ViewPager mViewPager;
    SlidingTabLayout mTabs;
    TabsAdapter adapter;
    MyTask loginTask;
    public static final String STATE_highlightList="savedInstanceHighlightListObject";
    public static final String STATE_LunchList="savedInstanceLunchListObject";
    public static final String STATE_dinnerList="savedInstanceDinnerListObject";
    static public final String BaseURL="http://5482e8b1.ngrok.io/";
    private static int result = 0;

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Lifecycle_Test", "Home_Page_OnStartMethod is called");
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("Lifecycle_Test", "Home_Page_OnReStartMethod is called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Lifecycle_Test", "Home_Page_OnResumeMethod is called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("Lifecycle_Test", "Home_Page_OnPauseMethod is called");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("Lifecycle_Test", "Home_Page_OnStopMethod is called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle_Test", "Home_Page_OnDestroy is called");
        this.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        //savedInstanceState.putParcelableArrayList(AppLoading.STATE_MasterList, masterList);

        super.onSaveInstanceState(savedInstanceState);
        Log.d("Lifecycle_Test", "Home_Page_SaveInstance is called");

        //outState.putParcelableArrayList("MasterList",);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Lifecycle_Test", "onCreateMethodCalled");

        setContentView(R.layout.activity_home__page);

        Home_Page.toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(Home_Page.toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        onLoginButtonClickListener();

        NavigationDrawer_Frag navDrawerFragment=(NavigationDrawer_Frag)getSupportFragmentManager().findFragmentById(R.id.navBar);
        navDrawerFragment.setUp(R.id.navBar, (DrawerLayout) findViewById(R.id.dlayout), toolbar);

    }

/*
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        Log.d("Home_Page_Debug","onCreateViewMethodCalled");
        return super.onCreateView(parent, name, context, attrs);
    }
*/

    public void onLoginButtonClickListener() {

        if (isOnline()) {
            loginTask = new MyTask();
            String uri = BaseURL + "FoodManics.Server/api/jsonServices/getItemsList";
            loginTask.execute(uri);

            Log.d("RestURL", uri);
            String taskStatus = String.valueOf(loginTask.getStatus());
            Log.d("TaskStatus", taskStatus);
        } else {
            Toast.makeText(Home_Page.this, "Network isn't available", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, CuisineList> {

        View mProgressView = findViewById(R.id.progressBarSignIn);
        ProgressDialog progressDialog = new ProgressDialog(Home_Page.this);
        private ListItem listItem;
        private ArrayList<ListItem> highlights_List, lunch_List, dinner_List;
        CuisineList cList;
        //MasterListItem masterListItem;

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
            //mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {

            Toast.makeText(Home_Page.this, values[0], Toast.LENGTH_SHORT).show();
        }

        //Whatever goes in here is out of control of Main thread, execution will be in background and may take its own time
        @Override
        protected CuisineList doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            InputStream in_stream=null;
            ByteArrayOutputStream out_stream=null;
            ArrayList<ListItem> tmpList;

            publishProgress("Login in progress...");
            cList = parseFeed(content);
            highlights_List = cList.getHighlights();
            lunch_List = cList.getLunch();
            dinner_List = cList.getDinner();

            tmpList=(ArrayList<ListItem>)highlights_List.clone();
            tmpList.addAll(lunch_List);
            tmpList.addAll(dinner_List);

 /*           Log.d("Debug_TmpListSize",""+tmpList.size());
            if(tmpList != null){
                for (ListItem item : tmpList) {
                    try {
                        String finalURL = BaseURL + item.getItemImageResourceValue();
                        Log.d("ImageURLs:", finalURL);
                        in_stream = (InputStream) new URL(finalURL).getContent();

                        //Compressing and converting to Byte Array saves space, image byte arrays have half the size of the other method used, below
                        *//*Bitmap bitmap;
                        bitmap= BitmapFactory.decodeStream(in_stream);
                        Log.d("Debug_HomePage_20", "CHeckOutputByteArrayExceptionBefore");
                        out_stream = new ByteArrayOutputStream();
                        Log.d("Debug_HomePage_20", "CHeckOutputByteArrayExceptionAfter");
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out_stream);
                        byte[] bytes = out_stream.toByteArray();
                        //item.setImageBitmap(bitmap);*//*


                        byte[] bytes1;
                        bytes1=IOUtils.toByteArray(in_stream);
                        Log.d("Debug_OStreamBitSize:", "" + bytes1.length);
                        item.setImageBitmap(bytes1);
                        Log.d("Debug_CompressBitSize:", "" + bytes1.length);



                        //masterList1.add(item);
                        in_stream.close();
                        //out_stream.close();
                    }
                    catch(Exception e){
                        e.printStackTrace();

                    }
                    finally{
                        try {
                            if(in_stream != null) {
                                in_stream.close();
                            }
                                *//*if(out_stream!=null){
                                out_stream.close();
                            }*//*
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else{
                //Define error for  json parsing failure scenario
            }
 */           return cList;
        }

        @Override
        protected void onPostExecute(CuisineList result) {

            String taskStatus = String.valueOf(loginTask.getStatus());
            Log.d("TaskStatusinPostExecute", taskStatus);

            progressDialog.dismiss();
            //mProgressView.setVisibility(View.GONE);

            //masterList1=Result;

            if(result != null){
                Log.d("HomePage_masterListVal1", "");

                List<Fragment> fragmentList=new ArrayList<>();

                Bundle sendList = new Bundle();
                sendList.putParcelableArrayList(STATE_highlightList, result.getHighlights());
                QuickManuPreview_Fragment highlight_FragObj=new QuickManuPreview_Fragment();
                highlight_FragObj.setArguments(sendList);
                fragmentList.add(highlight_FragObj);

                sendList = new Bundle();
                sendList.putParcelableArrayList(STATE_LunchList, result.getLunch());
                LunchMenuPreview_Fragment lunch_FragObj=new LunchMenuPreview_Fragment();
                lunch_FragObj.setArguments(sendList);
                fragmentList.add(lunch_FragObj);

                sendList=new Bundle();
                sendList.putParcelableArrayList(STATE_dinnerList, result.getDinner());
                DinnerMenuPreview_Fragment dinner_FragObj=new DinnerMenuPreview_Fragment();
                dinner_FragObj.setArguments(sendList);
                fragmentList.add(dinner_FragObj);

                mViewPager= (ViewPager) findViewById(R.id.home_viewpager);
                adapter=new TabsAdapter(getSupportFragmentManager(),fragmentList);
                mViewPager.setAdapter(adapter);


                mTabs=(SlidingTabLayout)findViewById(R.id.tabs);
                mTabs.setViewPager(mViewPager);

             }
            //Log.d("RandomBitmap check:", "" + masterList1.get(3).getImageBitmap().getByteCount());
            //Intent homePageIntent = new Intent(Home_Page.this, Home_Page.class);
            //homePageIntent.putParcelableArrayListExtra(STATE_MasterList, masterList1);
            //startActivity(homePageIntent);
        }


        public CuisineList parseFeed(String content){

            masterList=new ArrayList<>();

            highlights_List=new ArrayList<>();
            lunch_List=new ArrayList<>();
            dinner_List=new ArrayList<>();

            try {
                    JSONArray jArray=new JSONArray(content);  //jsonResponse.optJSONArray("Android");

                    for(int i=0;i< jArray.length();i++){
                        JSONObject child=jArray.getJSONObject(i);
                        Log.d("Inside JsonArray Loop", "InsideJSon ArrayLoop");

                        String cuisine_Flag=child.getString("item_Flag");
                        if(cuisine_Flag.equals("Highlights")){
                            listItem=new ListItem();
                            listItem.setItemFlag(child.getString("item_Flag"));
                            listItem.setItemTitleValue(child.getString("item_Name"));
                            listItem.setItemDescriptionValue(child.getString("item_Description"));
                            listItem.setItemPriceValue(child.getString("item_Price"));
                            listItem.setItemRating(child.getString("item_Rating"));
                            listItem.setItemImageResourceValue(child.getString("item_Image_URI"));
                            highlights_List.add(listItem);
                        }else if(cuisine_Flag.equals("Lunch")){
                            listItem=new ListItem();
                            listItem.setItemFlag(child.getString("item_Flag"));
                            listItem.setItemTitleValue(child.getString("item_Name"));
                            listItem.setItemDescriptionValue(child.getString("item_Description"));
                            listItem.setItemPriceValue(child.getString("item_Price"));
                            listItem.setItemRating(child.getString("item_Rating"));
                            listItem.setItemImageResourceValue(child.getString("item_Image_URI"));
                            lunch_List.add(listItem);
                        }else if(cuisine_Flag.equals("Dinner")){
                            listItem=new ListItem();
                            listItem.setItemFlag(child.getString("item_Flag"));
                            listItem.setItemTitleValue(child.getString("item_Name"));
                            listItem.setItemDescriptionValue(child.getString("item_Description"));
                            listItem.setItemPriceValue(child.getString("item_Price"));
                            listItem.setItemRating(child.getString("item_Rating"));
                            listItem.setItemImageResourceValue(child.getString("item_Image_URI"));
                            dinner_List.add(listItem);
                        }
                   }
                cList=new CuisineList();
                cList.setHighlights(highlights_List);
                cList.setLunch(lunch_List);
                cList.setDinner(dinner_List);
                return cList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);

        Home_Page.toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(Home_Page.toolbar);

        Log.d("Lifecycle_Test","Home_Page_OnCreateMethod is called");

        if(savedInstanceState != null){

            masterList = savedInstanceState.getParcelableArrayList(AppLoading.STATE_MasterList);
            Log.d("Lifecycle_Test","Home_Page_SavedInstanceStateisNull");

        }
        else{
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Log.d("HomePage_masterListVal3", "");
                masterList = extras.getParcelableArrayList(AppLoading.STATE_MasterList);
                Log.d("HomePage_masterListVal4", "");
                //Log.d("HomePage_masterList", "" + masterList.size());
                // do something with the customer

                if(masterList != null){
                    Log.d("HomePage_masterListVal1", "");
                    //Log.d("HomePage_masterListVal", "" + masterList.get(2).getItemTitleValue());
                    Bundle sendList = new Bundle();
                    sendList.putParcelableArrayList("QuickManuListFrag", masterList);

                    Log.d("Assigning Classvalue", "In HomePage1");
                    List<Fragment> fragmentList=new ArrayList<>();
                    QuickManuPreview_Fragment obj=new QuickManuPreview_Fragment();
                    obj.setArguments(sendList);
                    fragmentList.add(obj);

                    fragmentList.add(new LunchMenuPreview_Fragment());
                    fragmentList.add(new DinnerMenuPreview_Fragment());

                    Bundle sendList1 = new Bundle();
                    sendList1.putParcelableArrayList("QuickManuListFrag", masterList);

                    QuickManuPreview_Fragment obj1=new QuickManuPreview_Fragment();
                    obj.setArguments(sendList1);
                    fragmentList.add(obj1);

                    fragmentList.add(new LunchMenuPreview_Fragment());
                    fragmentList.add(new DinnerMenuPreview_Fragment());

                    mViewPager= (ViewPager) findViewById(R.id.home_viewpager);
                    adapter=new TabsAdapter(getSupportFragmentManager(),fragmentList);
                    mViewPager.setAdapter(adapter);


                    mTabs=(SlidingTabLayout)findViewById(R.id.tabs);
                    mTabs.setViewPager(mViewPager);

                    NavigationDrawer_Frag navDrawerFragment=(NavigationDrawer_Frag)getSupportFragmentManager().findFragmentById(R.id.navBar);
                    navDrawerFragment.setUp(R.id.navBar,(DrawerLayout)findViewById(R.id.dlayout), toolbar);
                }
            }
            else{
                Log.d("HomePage_masterListVal2", "");

                Intent loginPage=new Intent(Home_Page.this,SigninScreen.class);
                startActivity(loginPage);
            }
        }*/



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

