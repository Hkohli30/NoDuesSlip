package com.example.hkohli.orthodox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hkohli on 5/4/2016.
 */
public class JSONGetterClass extends AsyncTask<String,Void,String> {

    ProgressDialog progressDialog;
    Context context;
    public AsyncResponse delegate =null;


    public JSONGetterClass(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"Retrieving Data From Database",
                "ONLINE CONNECTOR",true,true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        try {
            delegate.processFinish(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String uri = params[0];
        BufferedReader bufferedReader = null;

        try
        {
            URL url = new URL(uri);
            HttpURLConnection con =(HttpURLConnection) url.openConnection();

            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;

            while( (json = bufferedReader.readLine() )!=null)
            {
                stringBuilder.append(json+"\n");
            }

            return stringBuilder.toString().trim();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
