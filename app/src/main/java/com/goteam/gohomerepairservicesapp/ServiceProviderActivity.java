package com.goteam.gohomerepairservicesapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderActivity extends AppCompatActivity {

    private RecyclerView availableServices_r;
    private RecyclerView currentServices_r;
    private SpAvailableServiceAdapter availableServiceAdapter;
    private SpCurrentServiceAdapter currentServiceAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;
    private ArrayList<Service> currentServices;
    private ArrayList<Service> availableServices;
    private ServiceProvider serviceProvider;
    private FirebaseUser firebaseUser;
    private String uid;
    private TextView companyName;
    private String companyName_s;




    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String TAG = "ServiceProviderActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_new);
        companyName=findViewById(R.id.s_company_name);
        availableServices = new ArrayList<Service>();
        currentServices = new ArrayList<Service>();
        loadRecyclers();
        setupRecyclers();

        //Load user
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        uid=firebaseUser.getUid();
        //Load current services owned by the sp
        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceProvider= dataSnapshot.child(uid).getValue(ServiceProvider.class);
                companyName_s=(String)dataSnapshot.child(uid).child("companyName").getValue();


                for (DataSnapshot snapshot : dataSnapshot.child(uid).child("services").getChildren()) {
                    String serviceName = (String) snapshot.child("serviceName").getValue();
                    Long serviceRate = (Long) snapshot.child("rate").getValue();
                    currentServices.add(new Service(serviceName, serviceRate));
                    currentServiceAdapter.notifyDataSetChanged();
                }


                companyName.setText(companyName_s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ServiceProviderActivity.this, "Canceled", Toast.LENGTH_SHORT).show();

            }
        });

      //Load avaiable services



    }


    private void loadRecyclers(){
        loadAvailableServices();
        availableServices_r = findViewById(R.id.availableServices);
        availableServices_r.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        availableServices_r.setLayoutManager(layoutManager);
        availableServiceAdapter = new SpAvailableServiceAdapter(availableServices);
        availableServices_r.setAdapter(availableServiceAdapter);


        currentServices_r = findViewById(R.id.currentServices);
        currentServices_r.setHasFixedSize(true);
        currentServices_r.setLayoutManager(layoutManager2);
        currentServiceAdapter = new SpCurrentServiceAdapter(currentServices);
        currentServices_r.setAdapter(currentServiceAdapter);

    }

    public void loadAvailableServices() {
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String serviceName = (String) snapshot.child("serviceName").getValue();
                    Long serviceRate = (Long) snapshot.child("rate").getValue();
                    availableServices.add(new Service(serviceName, serviceRate));
                    availableServiceAdapter.notifyDataSetChanged();
                }

                availableServiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setupRecyclers(){
        availableServiceAdapter.setOnCardClick(new SpAvailableServiceAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                currentServices.add(availableServices.get(position));
                serviceProvider.setServices(currentServices);
                currentServiceAdapter.notifyDataSetChanged();
                availableServiceAdapter.notifyDataSetChanged();
                database.getReference().child("Users").child(uid).setValue(serviceProvider);
            }
        });

        currentServiceAdapter.setOnCardClick(new SpCurrentServiceAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                currentServices.remove(position);
                serviceProvider.setServices(currentServices);
                currentServiceAdapter.notifyDataSetChanged();
                database.getReference().child("Users").child(uid).setValue(serviceProvider);

            }
        });


    }

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void btnAvailibility(View view){
        Intent intent = new Intent(this, AvailabilitySelectorActivity.class);
        startActivity(intent);
    }


    public void btnViewAvailibilityClicked(View view){
        Intent intent = new Intent(this, SpAvailabilityActivity.class);
        startActivity(intent);
    }

}
