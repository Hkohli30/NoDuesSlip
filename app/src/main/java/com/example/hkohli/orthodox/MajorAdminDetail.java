package com.example.hkohli.orthodox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MajorAdminDetail extends AppCompatActivity implements AsyncQueryResponse, View.OnClickListener{


    private static final String PAGE_URL = "http://www.coderzguild.16mb.com/QueryExecuter.php";

    private  static final String STUDENTS = "students";
    private  static final String SUPERVISORS = "supervisors";
    private  static final String OFFICE = "office";
    private  static final String TEACHER = "teacher";
    private  static final String COURSE = "course";
    private static final String BRANCH = "branch";
    private static final String BATCHES = "batches";
    private static final String DUES_RECORDS = "dues_records";
    private static final String SUPERVISOR_CATEGORY = "supervisor_category";

    private static final String TABLE_NAME[] = {STUDENTS,SUPERVISORS,
                                            OFFICE,TEACHER,COURSE,BRANCH,BATCHES,DUES_RECORDS
                                                    ,SUPERVISOR_CATEGORY   };
    private boolean DIALOG_BOOLEAN[] = {
                      false,false,false,false,false,false,false,false,false  };

    private static final String CTGRY[] = {"canteen","hostel","sports_officer",
                                            "transport","library","computer_center",
                                                "registrar","mess"};


    private Switch aSwitch;
    private TextView textView;
    String id;
    private ImageButton button,add_button;
    private Button button_ctg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_admin_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundleVal");

        id = bundle.getString("id");
        String name = bundle.getString("name");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        String email = bundle.getString("email");
        String phone = bundle.getString("phone");
        String verified = bundle.getString("verified");

        // GET ID's
        aSwitch = (Switch)findViewById(R.id.major_admin_switch);
        textView = (TextView)findViewById(R.id.major_admin_textview);
        button = (ImageButton)findViewById(R.id.major_admin_delete_button);
        add_button =(ImageButton)findViewById(R.id.major_admin_add_button);
        button_ctg =(Button)findViewById(R.id.major_admin_button_ctg);

        textView.setText("id : "+ id + "\n" +"name :"+ name + "\n"
                + "username :"+username + "\n" +"password :"+ password
                + "\n email : " + email + "\n phone : " + phone);

        if(verified.equals("0"))
        {
            aSwitch.setChecked(false);
            aSwitch.setText("NOT VERIFIED");
        }
        else if(verified.equals("1"))
        {
            aSwitch.setChecked(true);
            aSwitch.setText("VERIFIED");
        }


        // ADDING LISTENER
        button.setOnClickListener(this);
        add_button.setOnClickListener(this);
        button_ctg.setOnClickListener(this);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    QueryManager queryManager = new QueryManager(MajorAdminDetail.this,PAGE_URL);
                    queryManager.DELEGATE_RESPONSE = MajorAdminDetail.this;

              // String sql = "select username from college_admins where id = '"+id+"'";
               String sql = "UPDATE college_admins SET verified = '1' where " +
                       "id = '"+id+"';";
                    queryManager.execute("sql",sql);

                        aSwitch.setText("VERIFIED");

                } else {

                    QueryManager queryManager = new QueryManager(MajorAdminDetail.this,PAGE_URL);
                    queryManager.DELEGATE_RESPONSE = MajorAdminDetail.this;
                    String sql = "UPDATE college_admins SET verified = '0' where " +
                            "id = '"+id+"';";
                    queryManager.execute("sql", sql);
                    aSwitch.setText("NOT VERIFIED");
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
            if(v == button)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("ARE U SURE ?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                QueryManager queryManager = new QueryManager(MajorAdminDetail.this,
                                        PAGE_URL);
                                queryManager.DELEGATE_RESPONSE = MajorAdminDetail.this;

                                String sql = "delete from college_admins where " +
                                        "id = '" + MajorAdminDetail.this.id + "';";
                                queryManager.execute("sql", sql);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                      onBackPressed();
                                    }
                                },1500);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }


        if(v == add_button)
        {
            new AlertDialog.Builder(this).setTitle("Create DataBase")
                    .setMultiChoiceItems(TABLE_NAME, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            DIALOG_BOOLEAN[which] = isChecked;
                        }
                    })
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createTables();
                        }
                    }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                         deleteTables();
                        }
            }).create().show();
        }

        if(v == button_ctg)
        {
            for(int i = 0;i<CTGRY.length ;i++)
            {
                String sql = "INSERT INTO "+id+"_"+SUPERVISOR_CATEGORY+"(category) VALUES('"
                        +CTGRY[i]+"');";
                QueryManager queryManager = new QueryManager(MajorAdminDetail.this,PAGE_URL);
                queryManager.DELEGATE_RESPONSE = MajorAdminDetail.this;
                queryManager.execute("sql",sql);
            }
        }

    }


    private  void createTables()
    {

        String queries[] =
                {       // STUDENT QUERY
                        "CREATE TABLE "+id+"_"+STUDENTS+"" +
                        "(roll_no int(10) PRIMARY KEY NOT NULL,password varchar(20) NOT NULL," +
                        "image varchar(250)," +
                        "name varchar(40),contact_no int(12),course varchar(20)," +
                        "branch varchar(20),current_semester int(2),library_card int(10)," +
                                "email varchar(30));",

                        // SUPERVISOR QUERY

                        "CREATE TABLE "+id+"_"+SUPERVISORS+
                        "(id int(2) PRIMARY KEY AUTO_INCREMENT,name varchar(30) NOT NULL," +
                                "category varchar(30), " +
                                "username varchar(20) UNIQUE,password varchar(20)," +
                                "contact_no int(12),email varchar(30));",

                        // OFFICE USERS

                        "CREATE TABLE "+id+"_"+OFFICE+""+
                                "(id int(2) PRIMARY KEY AUTO_INCREMENT,name varchar(30) NOT NULL, " +
                                "username varchar(20),password varchar(20)," +
                                "contact_no int(12),email varchar(30));",

                        // TEACHER USER

                        "CREATE TABLE "+id+"_"+TEACHER+""+
                                "(id int(2) PRIMARY KEY AUTO_INCREMENT,name varchar(30) NOT NULL," +
                                "username varchar(20),password varchar(20), " +
                                "contact_no int(12),email varchar(30));",

                        // COURSE
                        "create table "+id+"_"+COURSE +
                        "(sno int(3) AUTO_INCREMENT PRIMARY KEY, course_name varchar(50) UNIQUE," +
                        "duration int(2));",

                        // BRANCH
                        "create table "+id+"_"+ BRANCH +""+
                        "(sno int(3) AUTO_INCREMENT PRIMARY KEY, branch_name varchar(50),course_name varchar(50));",

                        // BATCHES

                        "CREATE TABLE "+id+"_"+BATCHES +
                                "(sno int(5) AUTO_INCREMENT PRIMARY KEY ,batches varchar(12) UNIQUE);",

                        // NO-DUES
                        "CREATE TABLE "+id+"_"+DUES_RECORDS+
                                "(roll_no varchar(10) PRIMARY KEY, office bool default 0" +
                                ",canteen bool default 0,hostel bool default 0," +
                                "sports_officer bool default 0, transport bool default 0," +
                                "library bool default 0, computer_center bool default 0," +
                                "registrar bool default 0,mess bool default 0);",

                        // SUPERVISOR CATEGORY

                        "CREATE TABLE "+id+"_"+SUPERVISOR_CATEGORY+
                                "(sno int(2) AUTO_INCREMENT PRIMARY KEY,category varchar(30) UNIQUE);"
                };


        for(int i =0;i<TABLE_NAME.length;i++) {
            if (DIALOG_BOOLEAN[i]) {
                QueryManager queryManager = new QueryManager(MajorAdminDetail.this,PAGE_URL);
                queryManager.DELEGATE_RESPONSE = MajorAdminDetail.this;
                queryManager.execute("sql", queries[i]);
                DIALOG_BOOLEAN[i] = false;
            }
        }
    }

    private void deleteTables()
    {
        String querryDelete = "DROP TABLE "+id+"_";
        for(int i =0;i<TABLE_NAME.length;i++) {
            if (DIALOG_BOOLEAN[i]) {
                QueryManager queryManager = new QueryManager(MajorAdminDetail.this, PAGE_URL);
                queryManager.DELEGATE_RESPONSE = MajorAdminDetail.this;
                queryManager.execute("sql", querryDelete+TABLE_NAME[i]+";");
                DIALOG_BOOLEAN[i] = false;
            }
        }
    }


    @Override
    public void processResponse(String output) {
        if(output != null)
            Toast.makeText(MajorAdminDetail.this, ""+output, Toast.LENGTH_SHORT).show();
    }

}
