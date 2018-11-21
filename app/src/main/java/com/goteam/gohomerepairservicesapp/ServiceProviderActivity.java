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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class ServiceProviderActivity extends AppCompatActivity {

    private RecyclerView availableServices_r;
    private RecyclerView currentServices_r;
    private ServiceAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinkedList<Service> services;
    private TextView welcomeMessage, userEmail;
    private ServiceProvider serviceProvider;
    private FirebaseUser firebaseUser;



    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String TAG = "ServiceProviderActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_new);
        //Load user
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        final String uid=firebaseUser.getUid();

        database.getReference().child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceProvider = dataSnapshot.child(uid).getValue(ServiceProvider.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        services = serviceProvider.getServices();

        loadRecyclers();

    }


    private void loadRecyclers(){

        availableServices_r = findViewById(R.id.availableServices);
        availableServices_r.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        availableServices_r.setLayoutManager(layoutManager);
        adapter = new ServiceAdapter(services);
        availableServices_r.setAdapter(adapter);

    }

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
/*
    private void loadUserInformation() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        welcomeMessage.setText(String.format(getString(R.string.profile_welcome), firebaseUser.getDisplayName()));
        userEmail.setText(firebaseUser.getEmail());
    }

*/
    public void btnProfileInfoClicked(View view) {
        Intent intent = new Intent(ServiceProviderActivity.this, ProviderInfo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void btnAddServiceClicked(View view) {
    }
/*
    public void loadServices() {
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String serviceName = (String) snapshot.child("serviceName").getValue();
                    Long serviceRate = (Long) snapshot.child("rate").getValue();
                    services.add(new Service(serviceName, serviceRate));
                    adapter.notifyDataSetChanged();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpList() {
        adapter.setOnCardClick(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                final AlertDialog inputDialog = new AlertDialog.Builder(ServiceProviderActivity.this)
                        .setTitle("Edit Rate").setView(R.layout.editrate)
                        .setPositiveButton("Done", null)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create();

                inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialogInterface) {
                        inputDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dialog dialog = (Dialog) dialogInterface;
                                EditText rateInput = dialog.findViewById(R.id.rateEdit);
                                Double rate;

                                try {
                                    rate = Double.valueOf(rateInput.getText().toString());
                                } catch (NumberFormatException ex) {
                                    Toast.makeText(getApplicationContext(), "Please enter a valid rate.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                services.get(position).setRate(rate);
                                database.getReference("Services").child(services.get(position).getServiceName()).child("rate").setValue(rate);
                                adapter.notifyDataSetChanged();
                                dialogInterface.dismiss();
                            }
                        });
                    }
                });

                inputDialog.show();
            }

            @Override
            public void onDeleteClick(int position) {
                database.getReference("Services").child(services.get(position).getServiceName()).removeValue();
                services.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }
*/

}
