package com.example.hkohli.orthodox;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Hkohli on 4/27/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.StudentViewHolder> {

    ArrayList<Student> arrayList;
    ArrayList<Integer> array;

    public RVAdapter(ArrayList<Student> arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout_file,parent,false);
        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        array = new ArrayList<>();
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        holder.textView1.setText(arrayList.get(position).name);
        holder.textView2.setText(arrayList.get(position).info);
        holder.imageView.setImageResource(arrayList.get(position).image);
        holder.aSwitch.setChecked(arrayList.get(position).clear);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            CompoundButton.OnCheckedChangeListener
    {
        // DECLARING THE CARD LAYOUTS AND THE OTHER VIEWGROUPS
        CardView cardView;
        TextView textView1,textView2;
        ImageView imageView;
        Switch aSwitch;

        public StudentViewHolder(View the_layout) {
            super(the_layout);

            // GETTING THE REFFERENCE TO THE VIEWGROUPS
            the_layout.setOnClickListener(this);
            cardView = (CardView)the_layout.findViewById(R.id.cardview);
            imageView = (ImageView)the_layout.findViewById(R.id.row_imageview);
            textView1 = (TextView)the_layout.findViewById(R.id.row_textview1);
            textView2 = (TextView)the_layout.findViewById(R.id.row_textview2);
            aSwitch = (Switch)the_layout.findViewById(R.id.row_switch);

            aSwitch.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {

            }
            else
            {

            }
        }
    }
}
