package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView userEmail, userName, userAccountType;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseInst;
    DatabaseReference firebaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userEmail = findViewById(R.id.emailView);
        userName = findViewById(R.id.nameView);
        userAccountType = findViewById(R.id.accountTypeView);

        firebaseInst =FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRef =firebaseInst.getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
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

    public void btnLogoutClicked(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void loadUserInformation(){

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else {
            firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Account user= dataSnapshot.getValue(Account.class);
                    userEmail.setText(user.email);
                    userAccountType.setText(user.type);
                    userName.setText(user.name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
}
