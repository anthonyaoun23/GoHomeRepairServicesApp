package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private TextView userEmail, userName, userAccountType;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userEmail = findViewById(R.id.emailView);
        userName = findViewById(R.id.nameView);
        userAccountType = findViewById(R.id.accountTypeView);

        loadUserInformation();
    }

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void loadUserInformation() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        userEmail.setText("Email address: " + firebaseUser.getEmail());
        userName.setText("Name: " + firebaseUser.getDisplayName());

        DatabaseReference userInfoReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        userInfoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = null;
                String role = (String) dataSnapshot.child("role").getValue();

                if (role == null) {
                    Log.e(TAG, "Failed to fetch user info: role entry is not set");
                    Toast.makeText(ProfileActivity.this, "Failed to fetch user info", Toast.LENGTH_LONG).show();
                    return;
                }

                switch (role) {
                    case "admin":
                        user = dataSnapshot.getValue(Admin.class);
                        break;

                    case "homeowner":
                        user = dataSnapshot.getValue(Homeowner.class);
                        break;

                    case "provider":
                        user = dataSnapshot.getValue(ServiceProvider.class);
                        break;
                }

                if (user == null) {
                    Log.e(TAG, "Failed to fetch user info: could not create user object");
                    Toast.makeText(ProfileActivity.this, "Failed to fetch user info", Toast.LENGTH_LONG).show();
                    return;
                }

                userAccountType.setText("Role: " + user.getRoleName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
