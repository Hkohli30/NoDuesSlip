package com.example.hkohli.orthodox;

import org.json.JSONException;

/**
 * Created by Hkohli on 5/4/2016.
 */
public interface AsyncResponse {
    void processFinish(String output) throws JSONException;
}
