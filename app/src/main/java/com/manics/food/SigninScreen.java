package com.manics.food;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.manics.food.HttpAndRest.*;
import com.manics.food.ListView.ListItem;
import com.manics.food.foodmanics.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SigninScreen extends AppCompatActivity {

    static public final String httpurlMain = "http://f4f8b40b.ngrok.io/";
    private static int result = 0;
    MyTask loginTask;
    EditText et1;
    EditText et2;
    private Button loginBtn;
    private ImageButton signUpImgBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        loginBtn = (Button) findViewById(R.id.loginButton);
        signUpImgBtn = (ImageButton) findViewById(R.id.signUpImage);

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLoginButtonClickListener();
                        /*Log.d("AfterButtonclick", "I'm here in login button");
                        Intent homePageIntent = new Intent(SigninScreen.this, Home_Page.class);
                        startActivity(homePageIntent);*/
                    }
                }
        );

        signUpImgBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent homePageIntent = new Intent(SigninScreen.this, SignUp_Screen.class);
                        startActivity(homePageIntent);
                    }
                }
        );

        Log.d("AfterButtonclick", "I'm here");
    }

    public void onLoginButtonClickListener() {

            if (isOnline()) {
                    loginTask = new MyTask();
                    String uri = httpurlMain + "FoodManics.Server/api/jsonServices/getItemsList";
                    loginTask.execute(uri);

                    Log.d("Query", uri);
                    String taskStatus = String.valueOf(loginTask.getStatus());
                    Log.d("TaskStatus", taskStatus);
                } else {
                Toast.makeText(SigninScreen.this, "Network isn't available", Toast.LENGTH_SHORT).show();
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

    private class MyTask extends AsyncTask<String, String, String> {

        View mProgressView=findViewById(R.id.progressBarSignIn);
        ProgressDialog progressDialog=new ProgressDialog(SigninScreen.this);
        ListItem listItem;
        ArrayList<ListItem> masterList;
        //MasterListItem masterListItem;

        @Override
        protected void onPreExecute() {
            //mProgressView=findViewById(R.id.progressBarSignIn);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
            mProgressView.setVisibility(View.VISIBLE);
        }

        //Whatever goes in here is out of control of Main thread, execution will be in background and may take its own time
        @Override
        protected String doInBackground(String... params) {

            publishProgress("Login in progress...");
            String content = HttpManager.getData(params[0]);
            Log.d("ValueofOne", content);
            return content;
        }

        @Override
        protected void onPostExecute(String content) {

            String taskStatus = String.valueOf(loginTask.getStatus());
            Log.d("TaskStatusinPostExecute", taskStatus);

            progressDialog.dismiss();
            mProgressView.setVisibility(View.GONE);
            masterList=new ArrayList<>();
            //masterListItem=new MasterListItem();

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
                    //listItem.setItemImageURI(child.getString("item_Image_URI"));
                    masterList.add(listItem);
                    System.out.println(listItem.getItemTitleValue());
                    Log.d("JsonItemName:",listItem.getItemTitleValue());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

                //masterListItem.setMasterList(masterList);
                Intent homePageIntent = new Intent(SigninScreen.this, Home_Page.class);
                homePageIntent.putParcelableArrayListExtra("MasterList", masterList);
                startActivity(homePageIntent);

                Log.d("TaskStatus", taskStatus);

        }

        @Override
        protected void onProgressUpdate(String... values) {

            Toast.makeText(SigninScreen.this, values[0], Toast.LENGTH_SHORT).show();
        }
    }
}

