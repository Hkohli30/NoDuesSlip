package com.example.hkohli.orthodox;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SotInsertManager extends Fragment implements View.OnClickListener
                                                ,AsyncQueryResponse{


    public SotInsertManager() {
        // Required empty public constructor
    }

    private static final String SOT_URL ="http://www.coderzguild.16mb.com/SOTManager.php";

    private EditText edit_name,edit_username,edit_password,edit_phone,edit_email;
    private Button category_button,add_user_button;
    private String JSONValue ="";
    String CATEGORY_VALUE = "";
    String ID ="";
    String USER_TYPE = "";
    String DIALOG_ITEMS[];
    ArrayList<String> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sot_insert_manager, container, false);

        USER_TYPE = this.getArguments().getString("INSERT_TYPE");
        ID = this.getArguments().getString("id");

        // GETTING THE ID
        edit_name = (EditText)view.findViewById(R.id.sot_insert_name);
        edit_username = (EditText)view.findViewById(R.id.sot_insert_username);
        edit_password = (EditText)view.findViewById(R.id.sot_insert_password);
        edit_phone = (EditText)view.findViewById(R.id.sot_insert_phone);
        edit_email = (EditText)view.findViewById(R.id.sot_insert_email);

        category_button = (Button)view.findViewById(R.id.sot_insert_category);
        add_user_button =(Button)view.findViewById(R.id.sot_insert_add_user);

        category_button.setOnClickListener(this);
        add_user_button.setOnClickListener(this);

        if(!USER_TYPE.equals("supervisor"))
        {
            category_button.setVisibility(View.INVISIBLE);
        }
        else if(USER_TYPE.equals("supervisor"))
        {
            QueryManager queryManager = new QueryManager(getActivity(),
                    "http://www.coderzguild.16mb.com/categoryGetter.php");
            queryManager.DELEGATE_RESPONSE = this;
            queryManager.execute("id", ID);
        }
        // array List
        arrayList = new ArrayList<>();

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == category_button)
        {
            getItems();
            new AlertDialog.Builder(getActivity())
                    .setTitle("Select supervisor type")
                    .setSingleChoiceItems(DIALOG_ITEMS,0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CATEGORY_VALUE = DIALOG_ITEMS[which];
                        }
                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!CATEGORY_VALUE.equals(""))
                        category_button.setText(CATEGORY_VALUE +" Selected");
                }
            }).create().show();
        }

        if (v == add_user_button)
        {
            if (USER_TYPE.equals("supervisor") && CATEGORY_VALUE.equals(""))
            {
                Toast.makeText(getActivity(), "PLEASE SELECT SUPERVISOR TYPE", Toast.LENGTH_SHORT).show();
            }
            else
            {
                String name = edit_name.getText().toString().trim();
                String username = edit_username.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                String contact = edit_phone.getText().toString().trim();
                String email = edit_email.getText().toString().trim();

                QueryManager queryManager = new QueryManager(getActivity(),SOT_URL);
                queryManager.DELEGATE_RESPONSE = this;
                queryManager.execute("type",USER_TYPE,"id",ID,"name",name
                                        ,"username",username,"password",password
                                        ,"contact",contact,"email",email,
                                            "category",CATEGORY_VALUE);
            }
        }

    }

    @Override
    public void processResponse(String output) {
        if(output.startsWith("{"))
        {
            JSONValue = output;
            JSONValues();
        }
        else
        {
            Toast.makeText(getActivity(), ""+output.trim(), Toast.LENGTH_SHORT).show();
            Log.i("value",output.trim());
                if(output.trim().contentEquals("successfully inserted"))
                {
                    edit_name.setText("");
                    edit_username.setText("");
                    edit_password.setText("");
                    edit_phone.setText("");
                    edit_email.setText("");
                    category_button.setText("Select Category");
                }
        }

    }

    private void getItems()
    {

            DIALOG_ITEMS = new String[9];
            DIALOG_ITEMS[0] = "";
            DIALOG_ITEMS[1] = arrayList.get(0);
            DIALOG_ITEMS[2] = arrayList.get(1);
            DIALOG_ITEMS[3] = arrayList.get(2);
            DIALOG_ITEMS[4] = arrayList.get(3);
            DIALOG_ITEMS[5] = arrayList.get(4);
            DIALOG_ITEMS[6] = arrayList.get(5);
            DIALOG_ITEMS[7] = arrayList.get(6);
            DIALOG_ITEMS[8] = arrayList.get(7);

    }

    private  void JSONValues()
    {
        if(JSONValue != null)
        {
            try {
                JSONObject obj = new JSONObject(JSONValue);
                JSONArray jsonArray = obj.getJSONArray("result");

                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonobj = jsonArray.getJSONObject(i);
                    arrayList.add(jsonobj.getString("category"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
