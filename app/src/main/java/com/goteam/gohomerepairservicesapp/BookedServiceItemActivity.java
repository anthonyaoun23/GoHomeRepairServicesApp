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

import com.google.firebase.auth.FirebaseAuth;
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

    private int minuteLocal;
    private int hourOfDayLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service_item);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        provider= (ServiceProvider) bundle.getSerializable("Provider");
        homeowner=(Homeowner)bundle.getSerializable("Homeowner");
        database=FirebaseDatabase.getInstance();
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
         public void onAddClick(final int position) {
             final Dialog inputDialog = new Dialog(BookedServiceItemActivity.this);
             inputDialog.setContentView(R.layout.addbooking);
             final Button done=inputDialog.findViewById(R.id.done_button);
             final Button cancel=inputDialog.findViewById(R.id.cancel_button);
                     inputDialog.setTitle("Add Booking");
            cancel.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputDialog.cancel();
                }
            });


             inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                 String bookingName;
                 int pos;

                 @Override
                 public void onShow(final DialogInterface dialogInterface) {
                     final Dialog dialog = (Dialog) dialogInterface;


                     RecyclerView recyclerView2 = dialog.findViewById(R.id.booking_recycler);
                     final EditText editText = dialog.findViewById(R.id.booking_name);

                     recyclerView2.setLayoutManager(new LinearLayoutManager(BookedServiceItemActivity.this));
                     AvailabilityAdapter bookAdapt =new AvailabilityAdapter(getAvail());
                     recyclerView2.setAdapter(bookAdapt);
                     bookAdapt.setOnCardClick(new AvailabilityAdapter.OnItemClickListener() {
                         @Override
                         public void onItemClick(int position) {
                             pos=position;
                             bookingName = editText.getText().toString().trim();

                             new TimePickerDialog(BookedServiceItemActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                 @Override
                                 public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                     hourOfDayLocal=hourOfDay;
                                     minuteLocal=minute;
                                     if(timeChecked(getAvail().get(pos), LocalTime.of(hourOfDay, minute))){
                                         selectedDateTime = LocalDateTime.of(selectedDateTime.toLocalDate(), LocalTime.of(hourOfDay, minute));
                                     }else{
                                         Toast.makeText(BookedServiceItemActivity.this, "Please select a valid time!", Toast.LENGTH_LONG).show();
                                         onItemClick(pos);
                                     }
                                 }
                             }, selectedDateTime.getHour(), selectedDateTime.getMinute(), true).show();


                         }
                     });
                    done.setOnClickListener(new Button.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             bookingName = editText.getText().toString().trim();
                             if(bookingName==null||bookingName.isEmpty()){
                                 editText.setError("You must specify a booking name");
                                 editText.requestFocus();
                             }else if(!timeChecked(getAvail().get(pos), LocalTime.of(hourOfDayLocal, minuteLocal))){
                                 Toast.makeText(BookedServiceItemActivity.this, "Please select a valid time!", Toast.LENGTH_LONG).show();
                             }else{

                                 Booking booking = new Booking(bookingName, provider, new TimeOfAvailability(selectedDateTime), services.get(position), services.get(position).getRate());
                                 homeowner.addBooking(booking);
                                 firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                 String uid = firebaseUser.getUid();
                                 database.getReference("Users").child(uid).setValue(homeowner);
                                 Dialog dialog = (Dialog) dialogInterface;
                                 dialogInterface.dismiss();
                             }
                         }
                     });
                 }
             });

             inputDialog.show();

         }
     });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BookedServiceItemActivity.this, HomeOwnerActivity.class));
    }

    private boolean timeChecked(TimeOfAvailability time, LocalTime selected) {

                LocalTime start = LocalTime.of(time.getHourStart(), time.getMinuteStart());
                LocalTime end = LocalTime.of(time.getHourEnd(), time.getMinuteEnd());

                if (selected.isAfter(start) && selected.isBefore(end)){
                    return true;
            }
            return false;
        }

}

