package com.example.hkohli.orthodox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

public class SearchViaNameRoll extends Fragment implements View.OnClickListener {

    public SearchViaNameRoll() {}

    CardView cardView_rollno,cardView_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_via_name_roll,container,false);
        cardView_name = (CardView)view.findViewById(R.id.search_via_name_searchCard);
        cardView_rollno = (CardView)view.findViewById(R.id.search_via_rollno_searchCard);

        cardView_name.setOnClickListener(this);
        cardView_rollno.setOnClickListener(this);

        return view;
    }


        //  ONCLICK EVENTS ON THE CARDS AND OTHERS
    @Override
    public void onClick(View v) {

        if(v == cardView_name)
        {

        }

        if(v == cardView_rollno)
        {

        }
    }
}
