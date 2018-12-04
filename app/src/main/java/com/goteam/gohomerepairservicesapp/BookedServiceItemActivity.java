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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;

public class BookedServiceItemActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private BookedServiceAdapter adapter;
    private ArrayList<Service> services;
    private ArrayList<String> ids;

    TextView nameOfServiceProvider, nameOfSPCompany, numberOfSP;
    Button addRatingButton;
    DatabaseReference user;
    String timeId, serviceName, serviceRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service_item);

        services = new ArrayList<>();
        ids = new ArrayList<>();

        recyclerView = findViewById(R.id.bookedServicesRV);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadAvailability();
        adapter = new BookedServiceAdapter(services);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//        setupList();

        nameOfServiceProvider = findViewById(R.id.nameOfServiceProvider);
        nameOfSPCompany = findViewById(R.id.nameOfSPCompany);
        numberOfSP = findViewById(R.id.numberOfSP);

        addRatingButton = findViewById(R.id.addARatingbutton);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");



    }

    public void loadAvailability() {
        ServiceProvider serviceProvider = (ServiceProvider) getIntent().getSerializableExtra("Editing");

        for(Service service : serviceProvider.getServices()){
            services.add(service);
            ids.add(service.getServiceName());
        }
    }

//    public void setupList() {
//        adapter.setOnCardClick(new AvailabilityAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(SpAvailabilityActivity.this, AvailabilitySelectorActivity.class);
//                intent.putExtra("key", ids.get(position));
//                startActivity(intent);
//            }
//        });
//
//    }




}
