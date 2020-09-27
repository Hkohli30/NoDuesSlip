package com.example.hkohli.orthodox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Hkohli on 5/5/2016.
 */
public class QueryManager extends AsyncTask<String,Void,String> {

    private Context context;
    private String REGISTER_URL;
    private ProgressDialog progressDialog;
    private String progress_title,progress_msg;
    StringEncoder stringEncoder = new StringEncoder();

    // RESULT PASSER
    public AsyncQueryResponse DELEGATE_RESPONSE = null;


    public QueryManager(Context context,String register_url)
    {
        this.context = context;
        REGISTER_URL = register_url;

        progress_title = "Processing Operation";
        progress_msg = "Trees that are slow to grow bears the best fruit : So Have Patience";
    }

    public QueryManager(Context context,String register_url,String progress_title,String progress_msg)
    {
        this.context = context;
        REGISTER_URL = register_url;
        this.progress_title = progress_title;
        this.progress_msg = progress_msg;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,progress_title,progress_msg,true,true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        DELEGATE_RESPONSE.processResponse(""+s);
    }

    @Override
    protected String doInBackground(String... params) {

        HashMap<String,String> data = new HashMap<>();
        for(int i=0;i<params.length;i=i+2)
        {
            data.put(params[i],params[i+1]);
        }

        String result = stringEncoder.sendPostRequest(REGISTER_URL,data);
        return result;
    }
}
