package com.manics.food.foodmanics;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manics.food.Home_Page;
import com.manics.food.HttpAndRest.HttpManager;
import com.manics.food.ListView.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class AppLoading extends AppCompatActivity {

    static public final String BaseURL = "http://f4f8b40b.ngrok.io/";
    private static int result = 0;
    MyTask loginTask;
    public static final String STATE_MasterList="savedInstanceMasterListObject";

    @Override
    protected void onResume() {
        super.onResume();
        //onLoginButtonClickListener();
        Log.d("AppLOading_OnResume","OnResumeLoading");
    }

    @Override
    protected void onStart() {
        super.onStart();

        //onLoginButtonClickListener();
        Log.d("AppLOading_OnStart", "OnStartLoading");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_loading);

        onLoginButtonClickListener();
        Log.d("AppLOading_OnCreate", "OnCreateLoading");

    }


    public void onLoginButtonClickListener() {

        if (isOnline()) {
            loginTask = new MyTask();
            String uri = BaseURL + "FoodManics.Server/api/jsonServices/getItemsList";
            loginTask.execute(uri);

            Log.d("RestURL", uri);
            String taskStatus = String.valueOf(loginTask.getStatus());
            Log.d("TaskStatus", taskStatus);
        } else {
            Toast.makeText(AppLoading.this, "Network isn't available", Toast.LENGTH_SHORT).show();
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

    private class MyTask extends AsyncTask<String, String, ArrayList<ListItem>> {

        View mProgressView = findViewById(R.id.progressBarSignIn);
        ProgressDialog progressDialog = new ProgressDialog(AppLoading.this);
        ListItem listItem;
        ArrayList<ListItem> masterList, masterList1;
        //MasterListItem masterListItem;

        @Override
        protected void onPreExecute() {
            //mProgressView=findViewById(R.id.progressBarSignIn);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {

            Toast.makeText(AppLoading.this, values[0], Toast.LENGTH_SHORT).show();
        }

        //Whatever goes in here is out of control of Main thread, execution will be in background and may take its own time
        @Override
        protected ArrayList<ListItem> doInBackground(String... params) {

            publishProgress("Login in progress...");
            String content = HttpManager.getData(params[0]);
            InputStream in=null;
            masterList = new ArrayList<>();
            masterList1 = new ArrayList<>();
            masterList = parseFeed(content);
            Log.d("ValueofOne", content);

            if(masterList != null){
                for (ListItem item : masterList) {
                    try {
                        String finalURL = BaseURL + item.getItemImageResourceValue();
                        Log.d("ImageURLs:", finalURL);
                        in = (InputStream) new URL(finalURL).getContent();
                        Bitmap bitmap;
                        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, in);

                        bitmap= BitmapFactory.decodeStream(in);
                        //item.setImageBitmap(bitmap);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        byte[] bytes = stream.toByteArray();
                        //setresult.putExtra("BMP", bytes);
                        item.setImageBitmap(bytes);
                        Log.d("BitMapSizeCheck:", "" + item.getImageBitmap().length);
                        masterList1.add(item);
                        in.close();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    finally{
                        try {
                            if(in != null){
                                in.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else{
                //Define error in json parsing screnario
            }
            return masterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ListItem> Result) {

            String taskStatus = String.valueOf(loginTask.getStatus());
            Log.d("TaskStatusinPostExecute", taskStatus);

            progressDialog.dismiss();
            mProgressView.setVisibility(View.GONE);

            masterList1=Result;

            //Log.d("RandomBitmap check:", "" + masterList1.get(3).getImageBitmap().getByteCount());
            Intent homePageIntent = new Intent(AppLoading.this, Home_Page.class);
            homePageIntent.putParcelableArrayListExtra(STATE_MasterList, masterList1);
            startActivity(homePageIntent);

            Log.d("TaskStatus", taskStatus);
        }


        public ArrayList<ListItem> parseFeed(String content){

            masterList=new ArrayList<>();

            try {
                JSONArray jArray=new JSONArray(content);  //jsonResponse.optJSONArray("Android");

                for(int i=0;i< jArray.length();i++){
                    JSONObject child=jArray.getJSONObject(i);
                    Log.d("Inside JsonArray Loop", "Failing here");
                    listItem=new ListItem();
                    listItem.setItemFlag(child.getString("item_Flag"));
                    listItem.setItemTitleValue(child.getString("item_Name"));
                    listItem.setItemDescriptionValue(child.getString("item_Description"));
                    listItem.setItemPriceValue(child.getString("item_Price"));
                    listItem.setItemRating(child.getString("item_Rating"));
                    listItem.setItemImageResourceValue(child.getString("item_Image_URI"));
                    masterList.add(listItem);
                    System.out.println(listItem.getItemTitleValue());
                    Log.d("JsonItemName:",listItem.getItemTitleValue());
                }
                return masterList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
                return null;
        }
    }
}
