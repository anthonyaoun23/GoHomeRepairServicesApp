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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service_item);

        nameOfServiceProvider = findViewById(R.id.nameOfServiceProvider);
        nameOfSPCompany = findViewById(R.id.nameOfSPCompany);
        numberOfSP = findViewById(R.id.numberOfSP);

        addRatingButton = findViewById(R.id.addARatingbutton);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        if (key != null) {
            timeId = key;
            findViewById(R.id.deleteTimeButton).setVisibility(View.VISIBLE);

            user.child("bookedServices").child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ServiceProvider serviceProvider = dataSnapshot.getValue(ServiceProvider.class);

                    if (serviceProvider == null)
                        return;

//                    serviceName = serviceProvider.
//                    serviceRate = String.valueOf(service.getRate());
//
//                    nameOfService.setText(serviceName);
//                    rateOfService.setText("$"+serviceRate);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            serviceName = "Service Name";
            serviceRate = "$0";

//            nameOfService.setText(serviceName);
//            rateOfService.setText("$"+serviceRate);
        }

    }


}
