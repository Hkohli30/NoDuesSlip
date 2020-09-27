package com.example.hkohli.orthodox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class RecyclerViewList extends AppCompatActivity {


    ArrayList<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_list);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv);
        initilize();
        LinearLayoutManager sglm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(sglm);
        rvAdapter = new RVAdapter(studentList);
        recyclerView.setAdapter(rvAdapter);
    }
    RVAdapter rvAdapter;


    public void initilize()
    {
        studentList = new ArrayList<>();
        studentList.add(new Student("Himanshu Kohli 2255", "college", R.mipmap.ic_launcher,true));
        studentList.add(new Student("Himanshu 2", "college", R.mipmap.ic_launcher,false));
        studentList.add(new Student("Himanshu 3", "college", R.mipmap.ic_launcher,true));
        studentList.add(new Student("Himanshu 4", "college", R.mipmap.ic_launcher,false));
        studentList.add(new Student("Himanshu 5", "college", R.mipmap.ic_launcher,true));
    }


}
