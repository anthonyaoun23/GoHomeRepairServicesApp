package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView userEmail, userName, userAccountType;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userEmail = findViewById(R.id.emailView);
        userName = findViewById(R.id.nameView);
        userAccountType = findViewById(R.id.accountTypeView);


        firebaseAuth = FirebaseAuth.getInstance();
        loadUserInformation();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(firebaseAuth.getCurrentUser() == null){
//            finish();
//            startActivity(new Intent(this, MainActivity.class));
//        }
//    }

    private void loadUserInformation(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user.getEmail() != null){
            userEmail.setText(user.getEmail());

        }
        if(user.getDisplayName() != null){
            userName.setText(user.getDisplayName());
        }


    }
}
