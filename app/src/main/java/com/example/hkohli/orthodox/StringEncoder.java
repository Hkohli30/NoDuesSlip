package com.example.hkohli.orthodox;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hkohli on 5/3/2016.
 */
public class StringEncoder {

    public String sendPostRequest(String requestURL,HashMap<String,String> postDataParams)
    {
        URL url;
        String response = "";

        try
        {
            url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setReadTimeout(15000);
            con.setReadTimeout(15000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            bufferedWriter.write(getPostDataString(postDataParams));

            // CLOSING THE BUFFER AND STREAM

            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            // CHECK RESPONSE
            int responsecode = con.getResponseCode();

            if(responsecode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                response = br.readLine();
            }
            else
            {
                response = "Error!!!";
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    // GENERATES THE POSTING STRING IN ENCRYPTED UTF-8 ENCRYPTION

    private String getPostDataString(HashMap<String,String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String,String> entry : params.entrySet())
        {

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));

        }
        return result.toString();
    }

}
