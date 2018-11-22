package com.goteam.gohomerepairservicesapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class SpAvailabilityActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private AvailabilityAdapter adapter;
    private ArrayList<TimeOfAvailability> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //temp
        times=new ArrayList<TimeOfAvailability>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_availibility);
        recyclerView = findViewById(R.id.availabilityRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadAvailibility();
        adapter = new AvailabilityAdapter(times);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    public void loadAvailibility(){
        //get availilability from firebase and store in "times array"
    }


    public void setupList(){
        adapter.setOnCardClick(new AvailabilityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Edit Availibility here
            }
        });

    }
}
