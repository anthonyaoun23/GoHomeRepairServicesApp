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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private EditText emailAddressToRegister;
    private EditText nameToRegister;
    private EditText passwordToRegister;

    private RadioGroup radioGroupAccountType;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference adminCreatedReference = database.getReference("Admin");

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        radioGroupAccountType = findViewById(R.id.radioGroup);
        emailAddressToRegister = findViewById(R.id.emailAddressRegister);
        nameToRegister = findViewById(R.id.nameRegister);
        passwordToRegister = findViewById(R.id.passwordRegister);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private boolean registerSimpleVerification(String email, String name, String password) {
        if (email.isEmpty()) {
            emailAddressToRegister.setError("Email is required");
            emailAddressToRegister.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddressToRegister.setError("Please enter a valid email");
            emailAddressToRegister.requestFocus();
            return false;
        }

        if (name.isEmpty()) {
            nameToRegister.setError("Name is required");
            nameToRegister.requestFocus();
            return false;
        }

        // this might offend people with non-ascii characters in their names
        /*if (!name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
            nameToRegister.setError("Name must consist of letters.");
            nameToRegister.requestFocus();
            return false;
        }*/

        if (password.isEmpty()) {
            passwordToRegister.setError("Password is required");
            passwordToRegister.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            passwordToRegister.setError("Password must have length of 5 or more characters");
            passwordToRegister.requestFocus();
            return false;
        }

        return true;
    }

    public void btnRegisterUserClicked(View view) {
        final String email = emailAddressToRegister.getText().toString().trim();
        final String password = passwordToRegister.getText().toString().trim();
        final String name = nameToRegister.getText().toString().trim();

        if (!registerSimpleVerification(email, name, password))
            return;

        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Processing", "Creating your account...", true);

        adminCreatedReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Boolean isCreated = dataSnapshot.getValue(Boolean.class);

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        User user;

                        if (!task.isSuccessful() || firebaseUser == null) {
                            Log.e(TAG, "Failed to create user", task.getException());
                            Toast.makeText(RegisterActivity.this, "Failed to create your account. Please try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String uid = firebaseUser.getUid();

                        int selectedButton = radioGroupAccountType.getCheckedRadioButtonId(); //get the selected account and switch through the options

                        switch (selectedButton) {
                            case (R.id.radioButton):
                                user = new Homeowner();
                                break;

                            case (R.id.radioButton2):
                                user = new ServiceProvider();
                                break;

                            case (R.id.radioButton3):
                                if (isCreated == null || !isCreated) {
                                    adminCreatedReference.setValue(true);
                                    user = new Admin();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "An admin account has already been created.", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                break;

                            default:
                                Log.e(TAG, "Unexpected button ID " + selectedButton);
                                return;
                        }

                        // set display name
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        firebaseUser.updateProfile(profileChangeRequest);

                        // save extra user info
                        database.getReference("Users").child(uid).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e(TAG, "Failed to save user info", task.getException());
                                            Toast.makeText(RegisterActivity.this, "Failed to create your account. Please try again.", Toast.LENGTH_LONG).show();
                                            return;
                                        }

                                        Toast.makeText(RegisterActivity.this, "You have successfully registered.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
