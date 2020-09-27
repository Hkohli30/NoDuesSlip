package com.example.hkohli.orthodox;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminSignupActivity extends AppCompatActivity implements AsyncQueryResponse {

    private EditText name,username,password,college,email,phone;
    private Button signup;
    String delegate ="";

    private static final String REGISTER_URL ="http://www.coderzguild.16mb.com/AdminInsert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        // Getting id's

        name = (EditText)findViewById(R.id.admin_signup_name);
        username =(EditText)findViewById(R.id.admin_signup_username);
        password =(EditText)findViewById(R.id.admin_signup_password);
        college =(EditText)findViewById(R.id.admin_signup_college);
        email =(EditText)findViewById(R.id.admin_signup_phone);
        phone = (EditText)findViewById(R.id.admin_signup_phone);

        signup = (Button)findViewById(R.id.admin_signup_button);


        // Listener On The Button
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    QueryManager queryManager = new QueryManager(AdminSignupActivity.this,
                            REGISTER_URL);
                    queryManager.DELEGATE_RESPONSE = AdminSignupActivity.this;
                    queryManager.execute("name", name.getText().toString().trim(),
                            "username", username.getText().toString().trim(),
                            "password", password.getText().toString().trim(),
                            "college", college.getText().toString().trim(),
                            "email", email.getText().toString().trim(),
                            "phone", phone.getText().toString().trim()
                    );
                }

            });
    }

    @Override
    public void processResponse(String output) {
        delegate = output;
        Toast.makeText(AdminSignupActivity.this, ""+delegate
                +" Registration. Your Account will be activated in 24 hrs"
                , Toast.LENGTH_SHORT).show();

            if(output.trim().startsWith("successfully"))
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 3000);
            }
    }
}
