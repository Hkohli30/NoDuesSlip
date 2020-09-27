package com.example.hkohli.orthodox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MajorAdmin extends AppCompatActivity implements AsyncResponse {

    ListView listView;
    ArrayList<String> arrayList;
    TextView textView;
    String JsonValue;
    JSONArray jsonArray = null;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_admin);

        listView = (ListView)findViewById(R.id.major_listview);
        textView =(TextView)findViewById(R.id.major_textview);

        arrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter(MajorAdmin.this,
                R.layout.major_admin_listview,R.id.major_admin_listview_textview, arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(arrayList.get(position) + " " + position);

                try {
                    Bundle bundle = getBundle(position);
                    Intent intent = new Intent(MajorAdmin.this, MajorAdminDetail.class);
                    intent.putExtra("bundleVal", bundle);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Bundle getBundle(int position) throws JSONException {
        Bundle bundle = new Bundle();
        JSONObject j = jsonArray.getJSONObject(position);
        bundle.putString("id", j.getString("id"));
        bundle.putString("name", j.getString("name"));
        bundle.putString("username",j.getString("username"));
        bundle.putString("password",j.getString("password"));
        bundle.putString("college", j.getString("college"));
        bundle.putString("email",j.getString("email"));
        bundle.putString("phone", j.getString("phone"));
        bundle.putString("verified", j.getString("verified"));
        return bundle;
    }

    public void addData() throws JSONException
    {
        int i;
        if(JsonValue != null)
        {
            JSONObject jsonObject = new JSONObject(JsonValue);
            jsonArray = jsonObject.getJSONArray("result");

            for( i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                String data = "id : "+jsonObject2.getString("id") +
                        " -- name :"+jsonObject2.getString("name") +
                        " --college :"+jsonObject2.getString("college");
                arrayList.add(data);
            }
        }

        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void processFinish(String output) throws JSONException {
           if(output != null) {
               JsonValue = output;
               addData();
           }
    }

    public void jsonGetFunction()
    {
        // Get JSON
        JSONGetterClass jsonGetterClass = new JSONGetterClass(this);
        jsonGetterClass.delegate = this;
        jsonGetterClass.execute("http://www.coderzguild.16mb.com/getData.php");
    }


    // ON RESUME IS CALLED IN THE START SO IT AUTOMATICALLY CALLS THE JSON FUNCTION
    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        jsonGetFunction();
    }
}
