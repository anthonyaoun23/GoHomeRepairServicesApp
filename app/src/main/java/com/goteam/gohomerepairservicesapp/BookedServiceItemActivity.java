package com.goteam.gohomerepairservicesapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

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
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private LocalDateTime selectedDateTime;
    private android.text.format.Time addedBooking;
    private Homeowner homeowner;

    private TextView nameOfServiceProvider, numberOfSP;
    private Button addRatingButton;
    private ServiceProvider provider;
    private TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service_item);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        provider= (ServiceProvider) bundle.getSerializable("Provider");
        homeowner=(Homeowner)bundle.getSerializable("homeowner");
        nameOfServiceProvider = findViewById(R.id.nameOfServiceProvider);
        numberOfSP = findViewById(R.id.numberOfSP);
        addRatingButton = findViewById(R.id.addARatingbutton);
        numberOfSP.setText(provider.getPhoneNumber());
        nameOfServiceProvider.setText(provider.getCompanyName());
        selectedDateTime = LocalDateTime.now();

        ratingText = findViewById(R.id.ratingText);
        checkForExtra();

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

    public void checkForExtra(){
        Bundle extras = getIntent().getExtras();
        float rating;

        if (extras != null) {
            rating = extras.getFloat("rating");
            if(this.provider.getRating()!=0){
                float currentRating = (float) this.provider.getRating();
                rating = (currentRating+rating)/2;
            }
            this.provider.setRating((double)rating);
        }
        this.ratingText.setText(ratingText.getText().toString()+" "+provider.getRating());
    }

    public void addRatingButton(View view){

        Intent intent = new Intent(BookedServiceItemActivity.this, ServiceRatingActivity.class);

        intent.putExtra("Provider", this.provider);
        startActivity(intent);
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
             final AlertDialog inputDialog = new AlertDialog.Builder(BookedServiceItemActivity.this)
                     .setTitle("Add Booking").setView(R.layout.addbooking)
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
                     Dialog dialog = (Dialog) dialogInterface;

                     RecyclerView recyclerView2 = dialog.findViewById(R.id.booking_recycler);
                     recyclerView2.setLayoutManager(new LinearLayoutManager(BookedServiceItemActivity.this));
                     AvailabilityAdapter bookAdapt =new AvailabilityAdapter(getAvail());
                     recyclerView2.setAdapter(bookAdapt);
                     bookAdapt.setOnCardClick(new AvailabilityAdapter.OnItemClickListener() {
                         @Override
                         public void onItemClick(int position) {

                             new TimePickerDialog(BookedServiceItemActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                 @Override
                                 public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                     selectedDateTime = LocalDateTime.of(selectedDateTime.toLocalDate(), LocalTime.of(hourOfDay, minute));
                                 }
                             }, selectedDateTime.getHour(), selectedDateTime.getMinute(), true).show();

                         }
                     });
                     inputDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             //check avail and
                             Dialog dialog = (Dialog) dialogInterface;
                             dialogInterface.dismiss();
                         }
                     });
                 }
             });

             inputDialog.show();

         }
     });


    }


    private void updateDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);

            for (TimeOfAvailability avail : provider.getAvailabilities().values()) {
                LocalDateTime start = LocalDateTime.of(avail.getYear(), avail.getMonth(), avail.getDay(), avail.getHourStart(), avail.getMinuteStart());
                LocalDateTime end = LocalDateTime.of(avail.getYear(), avail.getMonth(), avail.getDay(), avail.getHourEnd(), avail.getMinuteEnd());

                if (selectedDateTime.isAfter(start) && selectedDateTime.isBefore(end)){
                    //Add booking to homeOwner
            }
        }

    }




}