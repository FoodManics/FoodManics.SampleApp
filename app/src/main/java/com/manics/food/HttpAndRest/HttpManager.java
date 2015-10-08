package com.manics.food.HttpAndRest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yash on 9/28/2015.
 */
public class HttpManager {
    public static String getData(String uri) {
        BufferedReader reader = null;
        String line = "";

        try {
            Log.d("HttpManager", "Inside HttpManager");
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

             while (((line = reader.readLine()) != null)) {
                sb.append(line + "\n");
            }
            if (!(sb.toString().equals(""))) {
                return sb.toString();
            } else {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "0";
                }
        }

    }
}
