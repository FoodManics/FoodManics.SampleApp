package com.manics.food;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.manics.food.foodmanics.R;

public class SignUp_Screen extends AppCompatActivity {

    private static int result = 0;
    ProgressBar pb;
    EditText un1, pwd;
    View focusView = null;
    private MyTask restToRegister = new MyTask();
    private View mProgressView;
    private View mEmailLoginFormView;
    //private EditText mPasswordView;
    //private EditText mEmailView;
    private View mLoginFormView;
    // Textview output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        Button registerButton;

        mLoginFormView = findViewById(R.id.defLay);
        registerButton = (Button) findViewById(R.id.RegisterButton);

        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRegisterButtonClickListener();
                    }
                }
        );

        Log.d("AfterButtonclick", "I'm here");
    }

    public void onRegisterButtonClickListener() {


        // pb= (ProgressBar) findViewById(R.id.progressBar1);
        //pb.setVisibility(View.INVISIBLE);

        Log.d("Reached", "tkt");
        un1 = (EditText) findViewById(R.id.insert_Username);
        pwd = (EditText) findViewById(R.id.insert_Password);

        String userName = un1.getText().toString();
        String password = pwd.getText().toString();

        //String uri1 = "http://f365988c.ngrok.io/ValidateLoginRestService/api/login/validate?uName=" + userName + "&pwd=" + password + "";
        //Log.d("Query", uri1);

        //mPasswordView = (EditText)findViewById(R.id.password);
        //mEmailView=(EditText)findViewById(R.id.userName);

        //et1.setFocusable(false);
        //et2.setFocusable(false );

        un1.setError(null);
        pwd.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
           // pwd.setError(getString(R.string.error_invalid_password));
            focusView = pwd;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            un1.requestFocus();
            //un1.setError(getString(R.string.error_field_required));
            focusView = un1;
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
                //MyTask restToRegister = new MyTask();
                String uri = SigninScreen.httpurlMain + "/RegisterUsersAndroidApp/api/registerUser/?uName=" + userName + "&pwd=" + password + "";
                restToRegister.execute(uri);

                Log.d("Register Service uri", uri);
                String taskStatus = String.valueOf(restToRegister.getStatus());
                Log.d("RegisterServTaskStatus", taskStatus);

                //String result=HttpManager.getData("http://localhost:8090/ValidateLoginRestService/api/login/validate?uName=yash&pwd=yash");
                //Intent inten=new Intent("com.example.yash.myapp.welcome_screen");
                //task.notify();
                //requestData("http://f365988c.ngrok.io/ValidateLoginRestService/api/login/validate?uName=yash&pwd=yash");
                Log.d("ValueOfResult", "success");
            } else {
                Toast.makeText(SignUp_Screen.this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
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

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //pb.setVisibility(View.GONE);
            mProgressView = (ProgressBar) findViewById(R.id.progressBarRegister);
            mProgressView.setVisibility(true ? View.VISIBLE : View.GONE);
            //super.onPreExecute();
        }

        //Whatever goes in here is out of control of Main thread, execution will be in background and may take its own time
        @Override
        protected String doInBackground(String... params) {

            publishProgress("Registration in progress...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //String result1 = HttpManager.getData(params[0]);
            //String result2="there"+result1.trim()+"here";
            //result1=result1.trim();
            //Log.d("ValueofOne", result1);

            return "1";
        }

        @Override
        protected void onPostExecute(String s) {
            String taskStatus = String.valueOf(restToRegister.getStatus());
            Log.d("TaskStatusinPostExecute", taskStatus);

            s = s.trim();
            Log.d("ValueofOne", "here" + s + "there");

            mProgressView.setVisibility(false ? View.VISIBLE : View.GONE);

            result = Integer.parseInt(s);
            if ((result == 1)) {
                Log.d("TaskStatus", taskStatus);
                //     Log.d("ResultValue", result);
                Log.d("Reached1", "tkt2");

                Toast.makeText(SignUp_Screen.this, "Registration Successful. Login using your new credentials.", Toast.LENGTH_LONG).show();

                Intent registrIntent = new Intent(SignUp_Screen.this, SigninScreen.class);
                startActivity(registrIntent);

                //Log.d("TaskStatus", taskStatus);
            } else {
                Log.d("Failed", "tkt3");
                Toast.makeText(SignUp_Screen.this, "Registration failed, some Network issue", Toast.LENGTH_SHORT).show();
                focusView = un1;
                //RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
                //rb.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {

            Toast.makeText(SignUp_Screen.this, values[0], Toast.LENGTH_LONG).show();
        }
    }
}
