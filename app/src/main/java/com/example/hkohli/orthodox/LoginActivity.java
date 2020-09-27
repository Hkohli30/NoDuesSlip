package com.example.hkohli.orthodox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements AsyncQueryResponse,AsyncResponse
                                                                ,AdapterView.OnItemSelectedListener{

    private  static final String STUDENTS = "students";
    private  static final String SUPERVISORS = "supervisors";
    private  static final String OFFICE = "office";
    private  static final String TEACHER = "teacher";
    private static final String ADMIN_URL = "http://www.coderzguild.16mb.com/AdminLogin.php";
    private static final String LOGIN_URL = "http://www.coderzguild.16mb.com/LoginManager.php";
    private static final String STUDENT_URL = "http://www.coderzguild.16mb.com/StudentLogin.php";



    private Spinner spinner;
    private Spinner spinner2;
    private ArrayList<String> arrayList;
    private ArrayList<String> id_arrayList;
    private ArrayAdapter<String> adapter;
    private Button login_button;
    private EditText edit_user,edit_password;

    private String[] TYPE_USERS = {"Select User Type","Administrator","Teacher"
                                        ,"Supervisor","Student","Office"};

    private String spinner_college ="";
    private String spinner_type = "";
    private String college_id ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SPINNER REFFERENCE
        spinner = (Spinner)findViewById(R.id.login_spinner);
        spinner2 =(Spinner)findViewById(R.id.login_spinner2);
        edit_user =(EditText)findViewById(R.id.login_username_edittext);
        edit_password =(EditText)findViewById(R.id.login_password_edittext);

        login_button =(Button)findViewById(R.id.login_screen_button);

        //FLOATING BUTTON
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                        .setAction("Admin Signup", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(LoginActivity.this,AdminSignupActivity.class));
                            }
                        }).show();
            }
        });

        FloatingActionButton fabs_major = (FloatingActionButton)findViewById(R.id.fab_major);
        fabs_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_password.getText().toString().equals("hk7837")) {
                    edit_password.setText("");
                    startActivity(new Intent(LoginActivity.this, MajorAdmin.class));
                } else {
                    edit_password.setText("");
                    Snackbar.make(v, "WRONG PASSWORD", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        // SETTING OR APPLYING LISTENERS
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logUserIn();
            }
        });
        final CardView cardView = (CardView)findViewById(R.id.login_card_button);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setCardBackgroundColor(Color.parseColor("#80FF5555"));
            }
        });

        // INITIALIZE COLLEGE ARRAYLIST AND SET ADAPTER TO IT
            spinnerInitalizer();

    }

    private void logUserIn()
    {
        if(spinner_college.equals("") && spinner_type.equals(""))
            Toast.makeText(LoginActivity.this,
                    "Please Select Valid College and User Type", Toast.LENGTH_SHORT).show();

        else if(spinner_college.equals(""))
            Toast.makeText(LoginActivity.this, "Please Select Valid College", Toast.LENGTH_SHORT).show();

        else if(spinner_type.equals(""))
            Toast.makeText(LoginActivity.this, "Please Select Valid Type", Toast.LENGTH_SHORT).show();
        else
        {
            String username = edit_user.getText().toString().trim();
            String password = edit_password.getText().toString().trim();

            if(spinner_type.equals("Administrator"))
            {
                QueryManager queryManager = new QueryManager(this,ADMIN_URL);
                queryManager.DELEGATE_RESPONSE = this;
                queryManager.execute("username",username,"password",password,
                                                "college",spinner_college);

                Intent intent = new Intent(this,DrawerActivity.class);
                intent.putExtra("ID",college_id);
                intent.putExtra("USER_TYPE","admin");
                startActivity(intent);

            }
            else if(spinner_type.equals("Teacher"))
            {
                QueryManager queryManager = new QueryManager(this,LOGIN_URL);
                queryManager.DELEGATE_RESPONSE = this;
                queryManager.execute("username",username,"password",password,
                        "college",spinner_college,"id",college_id,"tbname",TEACHER);
            }
            else if(spinner_type.equals("Supervisor"))
            {
                QueryManager queryManager = new QueryManager(this,LOGIN_URL);
                queryManager.DELEGATE_RESPONSE = this;
                queryManager.execute("username",username,"password",password,
                        "college",spinner_college,"id",college_id,"tbname",SUPERVISORS);
            }
            else if(spinner_type.equals("Student"))
            {
                QueryManager queryManager = new QueryManager(this,STUDENT_URL);
                queryManager.DELEGATE_RESPONSE = this;
                queryManager.execute("username",username,"password",password,
                        "id",college_id);
            }
            else if(spinner_type.equals("Office"))
            {
                QueryManager queryManager = new QueryManager(this,LOGIN_URL);
                queryManager.DELEGATE_RESPONSE = this;
                queryManager.execute("username",username,"password",password,
                        "college",spinner_college,"id",college_id,"tbname",OFFICE);
            }

        }
    }

    public void spinnerInitalizer()
    {
        arrayList = new ArrayList<>();
        id_arrayList = new ArrayList<>();   // for id's
        adapter = new ArrayAdapter<String>(this,R.layout.signup_spinner_layout,
                R.id.signup_spinner_layout_textview,arrayList);
        adapter.setDropDownViewResource(R.layout.signup_spinner_layout);
        spinner.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter<>(this,R.layout.signup_spinner_layout,
                                R.id.signup_spinner_layout_textview,TYPE_USERS);
        adapter2.setDropDownViewResource(R.layout.signup_spinner_layout);
        spinner2.setAdapter(adapter2);
    }

    private void getCollegeNames()
    {
        JSONGetterClass jsonGetterClass = new JSONGetterClass(this);
        jsonGetterClass.delegate = this;
        jsonGetterClass.execute("http://www.coderzguild.16mb.com/CollegeGetter.php");
    }

    private void setCollegeNames(String valueName)
    {
        try {
            JSONObject jsonObject = new JSONObject(valueName);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
                if(jsonArray.length() > 0)
                {
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        arrayList.add(obj.getString("college"));
                        id_arrayList.add(String.valueOf(obj.getInt("id")));
                    }
                    adapter.notifyDataSetChanged();
                }
            else
                {
                    Toast.makeText(LoginActivity.this, "NO USER PRESENT IN SYSTEM", Toast.LENGTH_SHORT).show();
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // FOR THE CONFIRMATION OF THE QUERY OR THE RETURN RESULT
    @Override
    public void processResponse(String output) {

        if(output.trim().contains("successful admin"))
        {
            String[] val = output.split("_");
            Log.i("val",val[1]);
        }
        else
        {
            Toast.makeText(LoginActivity.this, ""+output.trim(), Toast.LENGTH_SHORT).show();
        }
    }

    // FOR THE JSON OBJECT
    @Override
    public void processFinish(String output) throws JSONException {
            if(output != null)
            {
                if(output.startsWith("{"))
                {
                    setCollegeNames(output.trim());
                }
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Error Reteriving college Names", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent == spinner)
        {
            if(position == 0)
            {
                spinner_college = "";
                college_id = "";
            }
            else
            {
                spinner_college = arrayList.get(position);
                college_id = id_arrayList.get(position - 1 );
            }

        }
        if(parent == spinner2)
        {
            if(position == 0)
            {
                spinner_type = "";
            }
            else
            {
                spinner_type = TYPE_USERS[position];
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!arrayList.isEmpty())
        {
            arrayList.clear();
            id_arrayList.clear();
        }
        arrayList.add("Select College");
        adapter.notifyDataSetChanged();
        getCollegeNames();
    }
}
