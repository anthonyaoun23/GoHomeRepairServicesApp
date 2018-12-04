package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class HomeOwnerActivity extends AppCompatActivity {

    EditText searchText;
    LinkedList<ServiceProvider> serviceProviders;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceProviders= new LinkedList<>();
        setContentView(R.layout.activity_home_owner);
        searchText = findViewById(R.id.search_input);
        setListeners();
        loadServiceProviders();
    }

    private void setListeners()

    {

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener()

        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchProvider(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    private void loadServiceProviders(){

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot user : dataSnapshot.getChildren()) {
                    if ((user.child("role").getValue()).equals("provider")) {
                        serviceProviders.add(user.getValue(ServiceProvider.class));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchProvider(String text){
        for(ServiceProvider provider : serviceProviders){
            if(provider.getCompanyName().trim().contains(text.trim())){
                //Add provider to recycler view
                System.out.println("FOUND: "+provider);
            }
        }
    }


    private void loadServices(ServiceProvider provider){
    }

    public void btnLogoutClicked(View view){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }



