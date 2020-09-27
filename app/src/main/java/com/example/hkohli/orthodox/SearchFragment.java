package com.example.hkohli.orthodox;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    CardView cardView;
    Spinner course,branch,year;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search,container,false);
        cardView =(CardView)view.findViewById(R.id.search_cardview);
        course = (Spinner)view.findViewById(R.id.search_spinner_course);
        branch =(Spinner)view.findViewById(R.id.search_spinner_branch);
        year =(Spinner)view.findViewById(R.id.search_spinner_year);

        settingSpinners();

        // SETTING THE LISTENERS

        // CARDVIEW LISTENER
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "toast", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    ArrayList<String> arrayList;

    public void settingSpinners()
    {
        arrayList = new ArrayList<>();
        arrayList.add("data");
        course.setAdapter(new ArrayAdapter<>(getContext(),
                R.layout.signup_spinner_layout,R.id.signup_spinner_layout_textview
                , arrayList));

        branch.setAdapter(new ArrayAdapter<>(getContext(),
                R.layout.signup_spinner_layout,R.id.signup_spinner_layout_textview
                ,arrayList));

        year.setAdapter(new ArrayAdapter<>(getContext(),
                R.layout.signup_spinner_layout,R.id.signup_spinner_layout_textview
                ,arrayList));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view.getId() == course.getId())
        {

        }

        if (view.getId() == branch.getId())
        {

        }

        if (view == year)
        {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), "Please Select the Valid Options", Toast.LENGTH_SHORT).show();
    }

}
