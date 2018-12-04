package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

public class BookedServiceItemActivity extends AppCompatActivity {

    TextView nameOfServiceProvider, nameOfSPCompany, numberOfSP;
    Button addRatingButton;
    DatabaseReference user;
    String timeId, serviceName, serviceRate;
    ServiceProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service_item);

        nameOfServiceProvider = findViewById(R.id.nameOfServiceProvider);
        numberOfSP = findViewById(R.id.numberOfSP);

        addRatingButton = findViewById(R.id.addARatingbutton);

        Intent intent = getIntent();
        provider = (ServiceProvider)intent.getSerializableExtra("Provider");
        numberOfSP.setText(provider.getPhoneNumber());
        nameOfServiceProvider.setText(provider.getCompanyName());


        }

    }
