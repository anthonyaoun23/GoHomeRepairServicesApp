package com.goteam.gohomerepairservicesapp;

import android.app.Dialog;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MyBookingsAdapter adapter;
    private ArrayList<Booking> bookings;
    private ArrayList<String> ids;
    private Button backToSearchPage;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        bookings = new ArrayList<>();
        ids = new ArrayList<>();

        recyclerView = findViewById(R.id.myBookingsRecyclerView);
        recyclerView.setHasFixedSize(true);

        backToSearchPage = findViewById(R.id.backToSearch);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadBookings();
        adapter = new MyBookingsAdapter(bookings);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        setupList();
    }

    public void loadBookings(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

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
                database.getReference("bookings").child(bookings.get(position).getBookingName()).removeValue();
                bookings.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

    }

    public void backToSearchPageClicked(View view){
        Intent intent = new Intent(MyBookingsActivity.this, HomeOwnerActivity.class);
        startActivity(intent);
    }
}
