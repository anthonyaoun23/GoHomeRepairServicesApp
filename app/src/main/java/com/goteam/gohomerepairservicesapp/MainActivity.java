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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText emailToLogin;
    private EditText passwordToLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailToLogin = findViewById(R.id.emailAddressLogin);
        passwordToLogin = findViewById(R.id.passwordLogin);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Logging you in...", true);
            switchToActivity(user.getUid());
        }
    }

    public void btnRegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void btnLoginClicked(View view) {
        login(emailToLogin.getText().toString(), passwordToLogin.getText().toString());
    }

    private void login(String email, String password) {
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

        Log.i(TAG, "Signing in");

        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Logging you in...", true);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = firebaseAuth.getUid();

                if (!task.isSuccessful() || user == null || uid == null) {
                    Exception ex = task.getException();

                    Log.w(TAG, "Failed to sign in", ex);

                    if (ex instanceof FirebaseAuthInvalidUserException || ex instanceof FirebaseAuthInvalidCredentialsException)
                        Toast.makeText(MainActivity.this, "Failed to sign in: Invalid credentials.", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Failed to sign in. Please try again later.", Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();

                    return;
                }

                switchToActivity(uid);
            }
        });
    }

    private void switchToActivity(String uid) {
        Log.i(TAG, "Fetching user information from database");
        //Checking what kind of user is logging in, if an admin, intent is set to the admin activity.
        database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String loginRole = (String) dataSnapshot.child("role").getValue();

                if ("admin".equals(loginRole)) {
                    startActivity(AdminActivity.class);
                } else if ("provider".equals(loginRole)) {
                    Boolean accountFinalized = (Boolean) dataSnapshot.child("account_finalized").getValue();

                    if (accountFinalized == null || !accountFinalized)
                        startActivity(ServiceProviderInfoActivity.class);
                    else
                        startActivity(ServiceProviderActivity.class);
                } else if ("homeowner".equals(loginRole)) {
                    startActivity(HomeOwnerActivity.class);
                } else {
                    Toast.makeText(MainActivity.this, "Unknown account type", Toast.LENGTH_SHORT).show();
                }

                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Login cancelled");
                Log.e(TAG, "Database error: " + databaseError.getDetails());
                progressDialog.dismiss();
            }
        });
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
