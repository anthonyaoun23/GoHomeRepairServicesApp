package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

public class BookedServiceItemActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerA;
    private RecyclerView recyclerView;
    private RecyclerView availabilityRecycler;
    private BookedServiceAdapter adapter;
    private AvailabilityAdapter availabilityAdapter;
    private ArrayList<Service> services;
    private ArrayList<TimeOfAvailability> availabilities;

    private TextView nameOfServiceProvider, numberOfSP;
    private Button addRatingButton;
    private ServiceProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service_item);
        Intent intent = getIntent();
        provider = (ServiceProvider)intent.getSerializableExtra("Provider");
        nameOfServiceProvider = findViewById(R.id.nameOfServiceProvider);
        numberOfSP = findViewById(R.id.numberOfSP);
        addRatingButton = findViewById(R.id.addARatingbutton);
        numberOfSP.setText(provider.getPhoneNumber());
        nameOfServiceProvider.setText(provider.getCompanyName());


        services = new ArrayList<>();
        recyclerView = findViewById(R.id.bookedServicesRV);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadServices();
        adapter = new BookedServiceAdapter(services);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        layoutManagerA = new LinearLayoutManager(this);
        availabilityAdapter= new AvailabilityAdapter(getAvail());
        availabilityRecycler= findViewById(R.id.sp_availabilityList);
        availabilityRecycler.setLayoutManager(layoutManagerA);
        availabilityRecycler.setAdapter(availabilityAdapter);
        availabilityRecycler.setHasFixedSize(true);
        availabilityRecycler.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        setListeners();






    }

    public ArrayList<TimeOfAvailability> getAvail(){
        ArrayList<TimeOfAvailability> timmyReturner = new ArrayList<>();
        for(TimeOfAvailability availability:provider.getAvailabilities().values() ){
            timmyReturner.add(availability);
        }
        return timmyReturner;
    }

    public void loadServices() {

        for(Service service : provider.getServices()){
            services.add(service);
        }
    }

    private void setListeners() {
     adapter.setOnCardClick(new BookedServiceAdapter.OnItemClickListener() {
         @Override
         public void onItemClick(int position) {
             onAddClick(position);
         }

         @Override
         public void onAddClick(int position) {

         }
     });


    }




}