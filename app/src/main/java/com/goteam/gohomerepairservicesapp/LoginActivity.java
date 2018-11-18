package com.goteam.gohomerepairservicesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText emailToLogin;
    private EditText passwordToLogin;
    private FirebaseAuth firebaseAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailToLogin = findViewById(R.id.emailAddressLogin);
        passwordToLogin = findViewById(R.id.passwordLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void btnLoginUserClicked(View view) {
        String email = emailToLogin.getText().toString();
        String password = passwordToLogin.getText().toString();

        if (email.isEmpty()) {
            emailToLogin.setError("Email is required");
            emailToLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailToLogin.setError("Please enter a valid email");
            emailToLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordToLogin.setError("Password is required");
            passwordToLogin.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Please wait...", "Processing...", true);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = firebaseAuth.getUid();

                if(!task.isSuccessful() || user == null || uid == null) {
                    Log.e(TAG, "Failed to sign in", task.getException());
                    Toast.makeText(LoginActivity.this, "Failed to sign in. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

                //Checking what kind of user is logging in, if an admin, intent is set to the admin activity.
                database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String loginRole=(String) dataSnapshot.child("role").getValue();
                        String uid = firebaseAuth.getUid();

                        if("admin".equals(loginRole)) {
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }else if ("provider".equals(loginRole)){
                            Intent intent = new Intent(LoginActivity.this, ServiceProviderActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            database.getReference("Users").child(uid).child("account_finalized").setValue(false);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
