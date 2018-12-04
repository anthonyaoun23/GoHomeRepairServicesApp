package com.goteam.gohomerepairservicesapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class SpAvailabilityActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private AvailabilityAdapter adapter;
    private ArrayList<TimeOfAvailability> times;
    private ArrayList<String> ids;
    private Button addAvailabilityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //temp
        times = new ArrayList<>();
        ids = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_availibility);
        recyclerView = findViewById(R.id.availabilityRecyclerView);
        recyclerView.setHasFixedSize(true);
        addAvailabilityButton = findViewById(R.id.addAvailabilityButton);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadAvailability();
        adapter = new AvailabilityAdapter(times);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        setupList();
    }


    public void loadAvailability() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

        database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("availabilities").getChildren()) {
                    times.add(snapshot.getValue(TimeOfAvailability.class));
                    ids.add(snapshot.getKey());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setupList() {
        adapter.setOnCardClick(new AvailabilityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SpAvailabilityActivity.this, AvailabilitySelectorActivity.class);
                intent.putExtra("key", ids.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SpAvailabilityActivity.this, ServiceProviderActivity.class));
    }

    public void addAvailabilityButtonClicked(View view){
        Intent intent = new Intent(SpAvailabilityActivity.this, AvailabilitySelectorActivity.class);
        startActivity(intent);
    }
}
