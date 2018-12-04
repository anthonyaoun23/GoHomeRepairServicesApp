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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MyBookingsAdapter adapter;
    private ArrayList<Booking> bookings;
    private ArrayList<String> ids;
    private Button backToSearchPage;
    private String uid;
    private FirebaseAuth auth;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        bookings = new ArrayList<>();
        ids = new ArrayList<>();

        recyclerView = findViewById(R.id.myBookingsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadBookings();
        adapter = new MyBookingsAdapter(bookings);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        setupList();
    }

    public void loadBookings(){

        database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("bookings").getChildren()) {
                    bookings.add(snapshot.getValue(Booking.class));
                    ids.add(snapshot.getKey());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setupList(){
        adapter.setOnCardClick(new MyBookingsAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                database.getReference("Users").child(uid).child("bookings").child(String.valueOf(position)).removeValue();
                bookings.remove(position);
                adapter.notifyItemRemoved(position);
            }

            public void onItemClick(final int position) {
                final AlertDialog inputDialog = new AlertDialog.Builder(MyBookingsActivity.this)
                        .setTitle("Booking Info").setView(R.layout.bookinginfo)
                        .setPositiveButton("Done", null).create();

                inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    TextView rate;
                    TextView companyName;
                    TextView time;

                    @Override
                    public void onShow(final DialogInterface dialogInterface) {
                        final Dialog dialog = (Dialog) dialogInterface;
                        Booking booking= bookings.get(position);
                        rate = dialog.findViewById(R.id.rate_of);
                        companyName = dialog.findViewById(R.id.company_name);
                        time = dialog.findViewById(R.id.time_of);
                        time.setText(String.format("%d:%d",booking.getDateTime().getHourStart(),booking.getDateTime().getMinuteStart()));
                        rate.setText(String.format("%s/hr",booking.getRate()));
                        companyName.setText(booking.getServiceProvider().getCompanyName());

                        inputDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
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

}