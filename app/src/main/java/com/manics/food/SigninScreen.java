package com.manics.food;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.manics.food.foodmanics.R;

public class SigninScreen extends AppCompatActivity {

    static public final String httpurlMain = "http://06bd2cdb.ngrok.io";
    private static int result = 0;
    //MyTask loginTask;
    EditText et1;
    EditText et2;
    private Button loginBtn, testBtn;
    private ImageButton signUpImgBtn;
    private View mProgressView;
    private View mEmailLoginFormView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        mProgressView = (ProgressBar) findViewById(R.id.progressBar1);
        //mEmailLoginFormView=findViewById(R.id.defLay);
        mLoginFormView = findViewById(R.id.defLay);
        loginBtn = (Button) findViewById(R.id.loginButton);
        signUpImgBtn = (ImageButton) findViewById(R.id.signUpImage);

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //onLoginButtonClickListener();
                        Log.d("AfterButtonclick", "I'm here in login button");
                        Intent homePageIntent = new Intent(SigninScreen.this, Home_Page.class);
                        startActivity(homePageIntent);
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

  /*  public void onLoginButtonClickListener() {


        // pb= (ProgressBar) findViewById(R.id.progressBar1);
        //pb.setVisibility(View.INVISIBLE);

        Log.d("Reached", "tkt");
        et1 = (EditText) findViewById(R.id.userName);
        et2 = (EditText) findViewById(R.id.Pwd);

        String userName = et1.getText().toString();
        String password = et2.getText().toString();

        //String uri1 = "http://f365988c.ngrok.io/ValidateLoginRestService/api/login/validate?uName=" + userName + "&pwd=" + password + "";
        //Log.d("Query", uri1);

        //mPasswordView = (EditText)findViewById(R.id.password);
        //mEmailView=(EditText)findViewById(R.id.userName);

        //et1.setFocusable(false);
        //et2.setFocusable(false );

        et1.setError(null);
        et2.setError(null);

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            et2.setError(getString(R.string.error_invalid_password));
            focusView = et2;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            et1.requestFocus();
            et1.setError(getString(R.string.error_field_required));
            focusView = et1;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            // mLoginFormView.setVisibility(true ? View.GONE : View.VISIBLE);
            //showProgress(true);

            if (isOnline()) {

                if(userName.equals("admin") && password.equals("manage")){

                    Toast.makeText(SigninScreen.this, "Network available. Login Success!!!", Toast.LENGTH_SHORT).show();
                    Intent welcomeCustomIntent = new Intent(SigninScreen.this, welcome_screen.class);
                    startActivity(welcomeCustomIntent);
                }
                else {
                    loginTask = new MyTask();
                    String uri = httpurlMain + "/ValidateLoginRestService/api/login/validate?uName=" + userName + "&pwd=" + password + "";
                    loginTask.execute(uri);

                    Log.d("Query", uri);
                    String taskStatus = String.valueOf(loginTask.getStatus());
                    Log.d("TaskStatus", taskStatus);
                }
                //String result=HttpManager.getData("http://localhost:8090/ValidateLoginRestService/api/login/validate?uName=yash&pwd=yash");
                //Intent inten=new Intent("com.example.yash.myapp.welcome_screen");
                //task.notify();
                //requestData("http://f365988c.ngrok.io/ValidateLoginRestService/api/login/validate?uName=yash&pwd=yash");
                Log.d("ValueOfResult", "success");
            } else {
                Toast.makeText(SigninScreen.this, "Network isn't available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onTestButtonClickListener() {

        //btn1.setOnClickListener(
        //      new View.OnClickListener() {

        //        @Override
        //      public void onClick(View v) {

        AlertDialog.Builder a_builder = new AlertDialog.Builder(SigninScreen.this);

        a_builder.setMessage("Do you want to close my app asshole?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        //alert.create();
        alert.setTitle("Alert!!!");
        alert.show();

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


    //@Override
    *//*//*public void onClick(View v)
    {
        Log.d("Reached", "tkt");
        EditText et1=(EditText) findViewById(R.id.userName);
        EditText et2=(EditText) findViewById(R.id.Pwd);

        String a=et1.getText().toString();
        String b=et2.getText().toString();

        //Log.d(a,"userName");
        //Log.d(b,"Pwd");

        if (a.equals("yash") && b.equals("yash"))
        {
            Log.d("Reached1", "tkt2");
            RatingBar rb=(RatingBar) findViewById(R.id.ratingBar);
            rb.setVisibility(View.VISIBLE);

            Toast.makeText(HomeScreen.this,"Login Success", Toast.LENGTH_SHORT).show();

            //AlertDialog ad=new AlertDialog(android.app);
            //ad.setMessage("vg");
            //setContentView(R.layout.home_screen);
        }
        else
        {
            Log.d("Failed","tkt3");
            RatingBar rb=(RatingBar) findViewById(R.id.ratingBar);
            rb.setVisibility(View.VISIBLE);

        }
    }*//*


   *//* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }*//*

    *//*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    *//*

    protected void onDisplay(String msg) {
        // output.append(msg+"\n");
    }

    protected void requestData(String uri) {
        loginTask = new MyTask();
        loginTask.execute(uri);
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //pb.setVisibility(View.GONE);
            mProgressView.setVisibility(true ? View.VISIBLE : View.GONE);
            //super.onPreExecute();
        }

        //Whatever goes in here is out of control of Main thread, execution will be in background and may take its own time
        @Override
        protected String doInBackground(String... params) {

            publishProgress("Login in progress...");

            String result1 = HttpManager.getData(params[0]);
            //String result2="there"+result1.trim()+"here";
            //result1=result1.trim();
            Log.d("ValueofOne", result1);

            return result1;
        }

        @Override
        protected void onPostExecute(String s) {

            String taskStatus = String.valueOf(loginTask.getStatus());
            Log.d("TaskStatusinPostExecute", taskStatus);
            View focusView = null;


            //pb.isIndeterminate();
            //pb.bringToFront();
            s = s.trim();
            Log.d("ValueofOne", "here" + s + "there");

            mProgressView.setVisibility(false ? View.VISIBLE : View.GONE);

            //pb.setVisibility(View.VISIBLE);
            //pb.setVisibility(View.INVISIBLE);

            result = Integer.parseInt(s);
            if ((result == 1)) {
                Log.d("TaskStatus", taskStatus);
                //     Log.d("ResultValue", result);
                Log.d("Reached1", "tkt2");
                //RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
                //rb.setVisibility(View.VISIBLE);

                Toast.makeText(SigninScreen.this, "Network available. Login Success!!!", Toast.LENGTH_SHORT).show();

                //Intent homeIntent = new Intent(HomeScreen.this, welcome_screen.class);
                //Intent homeIntent = new Intent("com.example.yash.myapp.welcome_screen");
                Intent welcomeCustomIntent = new Intent(SigninScreen.this, welcomeScreenCustom.class);
                startActivity(welcomeCustomIntent);

                           *//*try {

                                //setContentView(R.layout.welcome_screen);
                                //AlertDialog ad=new AlertDialog(android.app);
                                //ad.setMessage("vg");
                                //setContentView(R.layout.home_screen);
                            *//*
                Log.d("TaskStatus", taskStatus);
            } else {
                Log.d("Failed", "tkt3");
                Toast.makeText(SigninScreen.this, "Invalid Login Credentials or network issue. Login Denied. Please try again in some time!!!", Toast.LENGTH_SHORT).show();
                focusView = et1;
                //RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
                //rb.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {

            Toast.makeText(SigninScreen.this, values[0], Toast.LENGTH_SHORT).show();
        }
    }
*/
}

