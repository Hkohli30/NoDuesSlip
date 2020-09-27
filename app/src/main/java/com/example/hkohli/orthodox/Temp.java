package com.example.hkohli.orthodox;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Temp extends AppCompatActivity implements AsyncQueryResponse,AsyncResponse{

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        frameLayout = (FrameLayout)findViewById(R.id.temp_frame);

//        AdminCourseManger adminCourseManger = new AdminCourseManger();
//        Bundle bundle = new Bundle();
//        String ID = "1";
//        bundle.putString("ID",ID);
//        adminCourseManger.setArguments(bundle);
//
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.temp_frame, adminCourseManger).commit();
//
//            SotInsertManager sotInsertManager = new SotInsertManager();
//            Bundle bundle = new Bundle();
//            bundle.putString("INSERT_TYPE","teacher");
//            bundle.putString("id","1");
//            sotInsertManager.setArguments(bundle);
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.temp_frame,sotInsertManager).commit();


//            StudentInserter studentInserter = new StudentInserter();
//            Bundle bundle = new Bundle();
//            bundle.putString("id","1");
//            studentInserter.setArguments(bundle);
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.temp_frame, studentInserter).commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.temp_frame, new UnderMaintanence()).commit();

//        QueryManager queryManager = new QueryManager(this,PAGE_URL);
//        queryManager.DELEGATE_RESPONSE = MajorAdminDetail.this;
//        queryManager.execute("sql",sql);

        JSONGetterClass jsonGetterClass = new JSONGetterClass(this);
        jsonGetterClass.delegate = this;
        jsonGetterClass.execute("http://www.coderzguild.16mb.com/tester.php");


    }
        String URL = "http://www.coderzguild.16mb.com/SOTManager.php";
    @Override
    public void processResponse(String output) {
          //  Log.i("JSON ", output);
    }

    @Override
    public void processFinish(String output) throws JSONException {
      //  Log.i("JSON ", ""+output);
    }
}
