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
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailAddressToRegister;
    private EditText nameToRegister;
    private EditText passwordToRegister;
    private String email;
    private String password;
    private String name;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailAddressToRegister = findViewById(R.id.emailAddressRegister);
        nameToRegister = findViewById(R.id.nameRegister);
        passwordToRegister = findViewById(R.id.passwordRegister);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private boolean registerSimpleVerification(){
        email = emailAddressToRegister.getText().toString().trim();
        password = passwordToRegister.getText().toString().trim();
        name = nameToRegister.getText().toString().trim();

        // name verification

        if(email.isEmpty()){
            emailAddressToRegister.setError("Email is required");
            emailAddressToRegister.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailAddressToRegister.setError("Please enter a valid email");
            emailAddressToRegister.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            passwordToRegister.setError("Password is required");
            passwordToRegister.requestFocus();
            return false;
        }

        if(password.length() < 6){
            passwordToRegister.setError("Password must have length of 6 or more characters");
            passwordToRegister.requestFocus();
            return false;
        }

        return true;
    }


    public void btnRegisterUserClicked(View view) {
        if(registerSimpleVerification()){
            final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Please Wait...", "Processing...", true);
            final Task<AuthResult> error = firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        User user = new User(name, email,null);

                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                            Toast.makeText(RegisterActivity.this, "You have successfully registered.", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        } else {
                                            // TODO display failure message
                                        }
                                    }
                                });
                    } else {
                        Log.e("ERROR", task.getException().toString());
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
