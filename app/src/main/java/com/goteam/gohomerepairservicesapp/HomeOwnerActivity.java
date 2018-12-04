package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class HomeOwnerActivity extends AppCompatActivity {
    private static final String TAG = "HomeownerActivity";
    LinkedList<ServiceProvider> serviceProviders;
    LinkedList<ServiceProvider> resultServiceProviders;
    LinkedList<String> services;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RadioGroup searchType;
    private RecyclerView providerRecycler;
    private HO_SPAdapter spRecyclerAdapter;
    private Spinner servicesSpinner;
    private ArrayAdapter<String> servicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceProviders = new LinkedList<>();
        resultServiceProviders = new LinkedList<>();
        services = new LinkedList<>();
        setContentView(R.layout.activity_home_owner);
        //searchText = findViewById(R.id.search_input);
        searchType = findViewById(R.id.searchType);
        providerRecycler = findViewById(R.id.provider_recycler);
        servicesSpinner = findViewById(R.id.servicesSpinner);

        //Setting up recyclerView
        spRecyclerAdapter = new HO_SPAdapter(resultServiceProviders);
        providerRecycler.setHasFixedSize(true);
        providerRecycler.setLayoutManager(new LinearLayoutManager(this));
        providerRecycler.setAdapter(spRecyclerAdapter);

        servicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                resultServiceProviders.clear();
                String item = servicesAdapter.getItem(i);

                if (i > 0 && item != null)
                    for (ServiceProvider provider : serviceProviders)
                        for (Service service : provider.getServices())
                            if (service.getServiceName() != null && service.getServiceName().toLowerCase().contains(item.toLowerCase()))
                                resultServiceProviders.add(provider);

                spRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                resultServiceProviders.clear();
            }
        });
        servicesSpinner.setAdapter(servicesAdapter);

        setListeners();
        loadServiceProviders();
    }

    private void setListeners() {
        spRecyclerAdapter.setOnCardClick(new HO_SPAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //  Intent intent=new Intent(HomeOwnerActivity.this, );
            }
        });

    }

    private void loadServiceProviders() {
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesAdapter.add("Select a service...");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Service service = ds.getValue(Service.class);
                    String name = service.getServiceName();

                    if (name != null)
                        services.add(name);
                }

                servicesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if ("provider".equals(user.child("role").getValue())) {
                        serviceProviders.add(user.getValue(ServiceProvider.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void searchProvider(String text) {
        text = text.trim();

        if (text.equals("")) {
            Toast.makeText(HomeOwnerActivity.this, "Please enter a valid search", Toast.LENGTH_LONG).show();
            return;
        }

        resultServiceProviders.clear();

        int selectedButton = searchType.getCheckedRadioButtonId();

        switch (selectedButton) {
            case (R.id.radioService):
                for (ServiceProvider provider : serviceProviders) {
                    for (Service service : provider.getServices()) {
                        if (service.getServiceName() != null && service.getServiceName().toLowerCase().contains(text.toLowerCase())) {
                            resultServiceProviders.add(provider);
                        }
                    }
                }

                break;

            case (R.id.radioRating):
                if (!text.matches("[0-5].?[0-9]?")) {
                    Toast.makeText(HomeOwnerActivity.this, "Please enter a rating between 0.0 and 5.0", Toast.LENGTH_LONG).show();
                    return;
                }

                for (ServiceProvider provider : serviceProviders)
                    if (provider.getRating() >= Double.valueOf(text))
                        resultServiceProviders.add(provider);

                break;

            case (R.id.radioTime):
                //TO IMPLEMENT BY NIC
        }

        spRecyclerAdapter.notifyDataSetChanged();
    }

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}



