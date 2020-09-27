package com.example.hkohli.orthodox;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements AsyncQueryResponse, AdapterView.OnItemSelectedListener {

    private static final String PAGE_URL = "http://www.coderzguild.16mb.com/SignupDataGetter.php";
    private static final String POST_PAGE_URL = "";

    private String ID;
    EditText name_edittext,phone_edittext;
    Spinner course,branch,semester,batch;
    Button signup;

    ArrayAdapter<String> arrayAdapterCourse,arrayAdapterBranch,arrayAdapterSemester,arrayAdapterBatch;
    ArrayList<String> courseArray,branchArray,semesterArray, batchArray;

    JSONArray jsonArrayBatch,jsonArrayCourse,jsonArrayBranch;
    String BATCH_HOLDER,COURSE_HOLDER,BRANCH_HOLDER,SEMESTER_HOLDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // SPINNER LAYOUT TEXTFIELD REFFERENCE
        TextView textView = (TextView)findViewById(R.id.signup_spinner_layout_textview);

        // SPINNERS REFFERENCE
        batch = (Spinner)findViewById(R.id.signup_batch_spinner);
        course = (Spinner)findViewById(R.id.signup_course_actv);
        branch = (Spinner)findViewById(R.id.signup_branch_actv);
        semester = (Spinner)findViewById(R.id.signup_currentsemester_actv);

        // EditText
        name_edittext = (EditText)findViewById(R.id.signup_name_edittext);
        phone_edittext = (EditText)findViewById(R.id.signup_phone_edittext);

        signup = (Button)findViewById(R.id.signup_button);
        // FUNCTION TO INITILIZE ARRAYLIST AND SPINNERS AND SET ADAPTERS
        initilizeSpinners();

        getSpinnersData();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name_edittext.getText().toString().trim().equals("") ||
                        phone_edittext.getText().toString().trim().equals("") ||
                        BRANCH_HOLDER.equals(branchArray.get(0)) ||
                        BATCH_HOLDER.equals(branchArray.get(0)) ||
                        COURSE_HOLDER.equals(courseArray.get(0)) ||
                        SEMESTER_HOLDER.equals(semesterArray.get(0))) {
                    Toast.makeText(SignUpActivity.this, ""
                            +"Please Fill All the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
            }
        });
    }

    public void getSpinnersData()
    {
        QueryManager queryManager = new QueryManager(this,PAGE_URL);
        queryManager.DELEGATE_RESPONSE = this;
        queryManager.execute("id","1");
    }


    public void initilizeSpinners()
    {
        batchArray = new ArrayList<>();
        courseArray = new ArrayList<>();
        branchArray  = new ArrayList<>();
        semesterArray  = new ArrayList<>();

        batchArray.add("Select Batch");
        courseArray.add("Select Course");
        branchArray.add("Select Branch");
        semesterArray.add("Current Semester");

        arrayAdapterCourse = new ArrayAdapter<>(this,R.layout.signup_spinner_layout,R.id.signup_spinner_layout_textview
                ,courseArray);
        arrayAdapterBranch = new ArrayAdapter<>(this,R.layout.signup_spinner_layout,R.id.signup_spinner_layout_textview
                ,branchArray);
        arrayAdapterSemester = new ArrayAdapter<>(this,R.layout.signup_spinner_layout,R.id.signup_spinner_layout_textview
                ,semesterArray);
        arrayAdapterBatch = new ArrayAdapter<String>(this,R.layout.signup_spinner_layout,R.id.signup_spinner_layout_textview
                ,batchArray);


        arrayAdapterCourse.setDropDownViewResource(R.layout.signup_spinner_layout);
        arrayAdapterBranch.setDropDownViewResource(R.layout.signup_spinner_layout);
        arrayAdapterSemester.setDropDownViewResource(R.layout.signup_spinner_layout);
        arrayAdapterBatch.setDropDownViewResource(R.layout.signup_spinner_layout);

        batch.setAdapter(arrayAdapterBatch);
        course.setAdapter(arrayAdapterCourse);
        branch.setAdapter(arrayAdapterBranch);
        semester.setAdapter(arrayAdapterSemester);

        batch.setOnItemSelectedListener(this);
        course.setOnItemSelectedListener(this);
        semester.setOnItemSelectedListener(this);
        branch.setOnItemSelectedListener(this);

        BATCH_HOLDER = batchArray.get(0);
        BRANCH_HOLDER = branchArray.get(0);
        SEMESTER_HOLDER = semesterArray.get(0);
        COURSE_HOLDER = courseArray.get(0);

    }

    @Override
    public void processResponse(String output) {
        if(output != null && !output.toString().trim().equals(""))
        {
            Log.i("JSON String :",output);
            setValueToSpinners(output);
        }
    }


    private void setValueToSpinners(String JSONString)
    {
        try {
            JSONObject jsonObject = new JSONObject(JSONString).getJSONObject("results");

            jsonArrayBatch = jsonObject.getJSONArray("batches");
            jsonArrayCourse = jsonObject.getJSONArray("courses");
            jsonArrayBranch = jsonObject.getJSONArray("branch");

            insertValues();     // batches and course

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertValues() throws JSONException {

        for(int i = 0;i < jsonArrayCourse.length();i++)
        {
            JSONObject jsonObject = jsonArrayCourse.getJSONObject(i);
            courseArray.add(jsonObject.getString("course_name"));
        }

        for(int i=0;i< jsonArrayBatch.length();i++)
        {
            JSONObject jsonObject = jsonArrayBatch.getJSONObject(i);
            batchArray.add(jsonObject.getString("batch"));
        }

        arrayAdapterBatch.notifyDataSetChanged();
        arrayAdapterCourse.notifyDataSetChanged();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent == batch)
        {
            BRANCH_HOLDER = batchArray.get(position);
        }
        if(parent == course)
        {
            COURSE_HOLDER = courseArray.get(position);

            branchArray.clear();
            branchArray.add("Select Branch");
            arrayAdapterBranch.notifyDataSetChanged();

            semesterArray.clear();
            semesterArray.add("Current Semeter");
            arrayAdapterSemester.notifyDataSetChanged();

            if(!COURSE_HOLDER.equals(courseArray.get(0)))
            {
                makeBranch();
            }

        }
        if(parent == branch)
        {
            BRANCH_HOLDER = branchArray.get(position);

            semesterArray.clear();
            semesterArray.add("Current Semeter");
            arrayAdapterSemester.notifyDataSetChanged();

            if(!BRANCH_HOLDER.equals(branchArray.get(0)))
            {
                makeSemester();
            }

        }
        if(parent == semester)
        {
            SEMESTER_HOLDER = semesterArray.get(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void makeBranch()
    {
        for(int i=0; i< jsonArrayBranch.length(); i++)
        {
            try {
                JSONObject jsonObject = jsonArrayBranch.getJSONObject(i);
                if(jsonObject.getString("course_name").equalsIgnoreCase(COURSE_HOLDER))
                {
                    branchArray.add(jsonObject.getString("branch_name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            arrayAdapterBranch.notifyDataSetChanged();
        }
    }

    private void makeSemester()
    {
        for(int i=0; i< jsonArrayBranch.length(); i++)
        {
            try {
                JSONObject jsonObject = jsonArrayBranch.getJSONObject(i);
                if(jsonObject.getString("branch_name").equals(BRANCH_HOLDER))
                {
                    int sem = Integer.parseInt(jsonObject.getString("duration"));
                    for(int j=1;j<=sem;j++)
                    {
                        semesterArray.add(""+j);
                    }
                }
                arrayAdapterBranch.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
