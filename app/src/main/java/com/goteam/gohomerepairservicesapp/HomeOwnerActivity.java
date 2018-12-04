package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class HomeOwnerActivity extends AppCompatActivity {
    private static final String TAG = "HomeownerActivity";
    LinkedList<ServiceProvider> serviceProviders;
    LinkedList<ServiceProvider> resultServiceProviders;
    LinkedList<String> services;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");
    private RadioGroup searchType;
    private RecyclerView providerRecycler;
    private HO_SPAdapter spRecyclerAdapter;
    private Spinner servicesSpinner;
    private Spinner ratingsSpinner;
    private ArrayAdapter<String> servicesAdapter;
    private ArrayAdapter<Integer> ratingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceProviders = new LinkedList<>();
        resultServiceProviders = new LinkedList<>();
        services = new LinkedList<>();
        setContentView(R.layout.activity_home_owner);
        searchType = findViewById(R.id.searchType);
        providerRecycler = findViewById(R.id.provider_recycler);
        servicesSpinner = findViewById(R.id.servicesSpinner);
        ratingsSpinner = findViewById(R.id.ratingsSpinner);
        providerRecycler.addItemDecoration(new DividerItemDecoration(providerRecycler.getContext(), DividerItemDecoration.VERTICAL));

        //Setting up recyclerView
        spRecyclerAdapter = new HO_SPAdapter(resultServiceProviders);
        providerRecycler.setHasFixedSize(true);
        providerRecycler.setLayoutManager(new LinearLayoutManager(this));
        providerRecycler.setAdapter(spRecyclerAdapter);

        servicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(servicesAdapter);
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

        ratingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new Integer[] {0, 1, 2, 3, 4, 5});
        ratingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingsSpinner.setAdapter(ratingsAdapter);
        ratingsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                resultServiceProviders.clear();
                Integer item = ratingsAdapter.getItem(i);

                if (item != null)
                    for (ServiceProvider provider : serviceProviders)
                        if (provider.getRating() >= item)
                            resultServiceProviders.add(provider);

                spRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                resultServiceProviders.clear();
            }
        });

        searchType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                int selectedPosition;

                switch (id) {
                    case R.id.radioService:
                        servicesSpinner.setVisibility(View.VISIBLE);
                        ratingsSpinner.setVisibility(View.GONE);

                        servicesSpinner.getOnItemSelectedListener().onItemSelected(null, servicesSpinner.getSelectedView(), servicesSpinner.getSelectedItemPosition(), servicesSpinner.getSelectedItemId());
                        break;
                    case R.id.radioTime:
                        servicesSpinner.setVisibility(View.GONE);
                        ratingsSpinner.setVisibility(View.GONE);

                        ratingsSpinner.getOnItemSelectedListener().onItemSelected(null, ratingsSpinner.getSelectedView(), ratingsSpinner.getSelectedItemPosition(), ratingsSpinner.getSelectedItemId());
                        break;
                    case R.id.radioRating:
                        servicesSpinner.setVisibility(View.GONE);
                        ratingsSpinner.setVisibility(View.VISIBLE);

                        selectedPosition = ratingsSpinner.getSelectedItemPosition();
                        ratingsSpinner.performItemClick(ratingsSpinner.getChildAt(selectedPosition), selectedPosition, ratingsSpinner.getItemIdAtPosition(selectedPosition));
                        break;
                }
            }
        });

        setListeners();
        fetchFromDatabase();
    }

    private void setListeners() {
        spRecyclerAdapter.setOnCardClick(new HO_SPAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                  Intent intent=new Intent(HomeOwnerActivity.this, BookedServiceItemActivity.class);
                  intent.putExtra("Provider", resultServiceProviders.get(position));
                  startActivity(intent);
            }
        });

    }

    private void fetchFromDatabase() {
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesAdapter.add("Select a service...");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Service service = ds.getValue(Service.class);

                    if (service == null)
                        continue;

                    String name = service.getServiceName();

                    if (name == null)
                        continue;

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

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}



