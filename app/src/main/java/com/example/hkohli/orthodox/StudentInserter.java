package com.example.hkohli.orthodox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentInserter extends Fragment implements AsyncQueryResponse{


    public StudentInserter() {
    }

    private static final String DUES_RECORDS = "dues_records";
    private  static final String STUDENTS = "students";
    private static final String URL_INSERT = "http://www.coderzguild.16mb.com/QueryExecuter.php";
    private TextView roll_text,password_text;
    private Button add;
    private String ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_student_inserter, container, false);

        ID = this.getArguments().getString("id");


        // Get ID
        roll_text = (TextView)view.findViewById(R.id.student_insert_roll);
        password_text =(TextView)view.findViewById(R.id.student_insert_password);
        add = (Button)view.findViewById(R.id.student_insert_button);

        roll_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                password_text.setText(roll_text.getText());
                return false;
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roll = roll_text.getText().toString().trim();
                String password = password_text.getText().toString().trim();

                String sql1 = "INSERT INTO "+ID+"_"+STUDENTS+"(roll_no,password) VALUES" +
                        "("+roll+",'"+password+"');";

                String sql2 = "INSERT INTO "+ID+"_"+DUES_RECORDS+"(roll_no) VALUES("+roll+")";


                QueryManager queryManager = new QueryManager(getActivity(),URL_INSERT);
                queryManager.DELEGATE_RESPONSE = StudentInserter.this;
                queryManager.execute("sql",sql1);

               queryManager = new QueryManager(getActivity(),URL_INSERT);
                queryManager.DELEGATE_RESPONSE = StudentInserter.this;
                queryManager.execute("sql",sql2);

            }
        });

        return view;
    }

    @Override
    public void processResponse(String output) {

        if(output != null)
        {
            Toast.makeText(getActivity(), ""+output.trim(), Toast.LENGTH_SHORT).show();
            if(output.trim().equals("successfully"))
            {
                roll_text.setText("");
                password_text.setText("");
            }
        }
    }
}
