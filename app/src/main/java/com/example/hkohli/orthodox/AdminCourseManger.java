package com.example.hkohli.orthodox;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminCourseManger extends Fragment implements View.OnClickListener,
                                                    AsyncQueryResponse {


    public AdminCourseManger() {}

    private static final String ARRAY_VALUE = "result";

    private static final String COURSE_GETTER_URL = "http://www.coderzguild.16mb.com/courseGetter.php";
    private static final String MANAGER_URL ="http://www.coderzguild.16mb.com/BcbManager.php";


    FloatingActionButton fabs_course,fabs_branch,fabs_batch;
    EditText edit_course,edit_branch,edit_batch,edit_course_duration;
    private String ID;
    private String SPINNER_VALUE;
    private String TYPE_OF_TABLE = "";

    Spinner course_select;
    ArrayList<String> arrayListCourses;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_course_manger, container, false);

        // GET THE ID
         ID = this.getArguments().getString("ID");

        // getting id's
        course_select =(Spinner) view.findViewById(R.id.admin_course_select_spinner);

        fabs_batch = (FloatingActionButton)view.findViewById(R.id.admin_batch_fabs);
        fabs_branch = (FloatingActionButton)view.findViewById(R.id.admin_branch_fabs);
        fabs_course = (FloatingActionButton)view.findViewById(R.id.admin_course_fabs);

        edit_branch =(EditText)view.findViewById(R.id.admin_course_manager_branch_name);
        edit_batch = (EditText)view.findViewById(R.id.admin_batch_edit);
        edit_course =(EditText)view.findViewById(R.id.admin_course_edit);
        edit_course_duration =(EditText)view.findViewById(R.id.admin_course_duration_edit);

        // SETTING LISTENERS
        fabs_batch.setOnClickListener(this);
        fabs_branch.setOnClickListener(this);
        fabs_course.setOnClickListener(this);

        // Array Assign
        arrayListCourses = new ArrayList<>();
        arrayListCourses.add("Select course name");
        spinnerSetup();

        // JSON VAlue for course
        getCourseValue();

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == fabs_batch)
        {
            String batch_value = edit_batch.getText().toString().trim();
            QueryManager queryManager = new QueryManager(getActivity(),MANAGER_URL);
            queryManager.DELEGATE_RESPONSE = this;
            queryManager.execute("type", "batches", "value1", batch_value,
                    "value2","bogus", "id", ID);
        }

        if (v == fabs_branch)
        {
            String branch_value = edit_branch.getText().toString().trim();
            if(!SPINNER_VALUE.equals(arrayListCourses.get(0)))
            {
                QueryManager queryManager = new QueryManager(getActivity(),MANAGER_URL);
                queryManager.DELEGATE_RESPONSE = this;
                queryManager.execute("type", "branch", "value1",branch_value,
                        "value2",SPINNER_VALUE, "id", ID);
            }
            else
            {
                Toast.makeText(getActivity(), "Please select a course name", Toast.LENGTH_SHORT).show();
            }

        }

        if (v == fabs_course)
        {
            String course_edit_val = edit_course.getText().toString().trim();
            String course_duration_edit_val = edit_course_duration.getText().toString().trim();

            QueryManager queryManager = new QueryManager(getActivity(),MANAGER_URL);
            queryManager.DELEGATE_RESPONSE = this;
            queryManager.execute("type","courses","value1",course_edit_val,
                                    "value2",course_duration_edit_val,"id",ID);

            arrayListCourses.clear();
            getCourseValue();
        }

    }

    // For GETTING RESULTS
    @Override
    public void processResponse(String output) {

        if(output != null)
        {
            if(output.startsWith("{") && !output.equalsIgnoreCase("successfully"))
            {
                parseJSONValue(output);
            }
            else
            {
                Toast.makeText(getActivity(), ""+output.trim(), Toast.LENGTH_SHORT).show();
                    if(output.trim().equalsIgnoreCase("successfully"));
                    {
                        edit_batch.setText("");
                        edit_course_duration.setText("");
                        edit_branch.setText("");
                        edit_course.setText("");
                    }
            }
        }
    }

    private void getCourseValue()       // Query Executer
    {
        QueryManager queryManager = new QueryManager(getActivity(),
                COURSE_GETTER_URL);
        queryManager.DELEGATE_RESPONSE = this;
        queryManager.execute("id", ID);
    }

    private void spinnerSetup()         // spinner setup and listener
    {
        spinnerAdapter = new ArrayAdapter<String>(getActivity(),R.layout.signup_spinner_layout,
                R.id.signup_spinner_layout_textview,arrayListCourses);

        spinnerAdapter.setDropDownViewResource(R.layout.signup_spinner_layout);
        course_select.setAdapter(spinnerAdapter);

        SPINNER_VALUE = arrayListCourses.get(0);
        course_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SPINNER_VALUE = arrayListCourses.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please select a course name", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void parseJSONValue(String JSONValue)
    {
        try {
            JSONObject jsonObject = new JSONObject(JSONValue);
            JSONArray jsonArray = jsonObject.getJSONArray(ARRAY_VALUE);

                for(int i = 0;i< jsonArray.length();i++)
                {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String data = json.getString("course_name");
                    arrayListCourses.add(data);
                }
                spinnerAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
