package com.goteam.gohomerepairservicesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailAddressToRegister;
    private EditText nameToRegister;
    private EditText passwordToRegister;

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

    public void btnRegisterUserClicked(View view) {

        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Please Wait...", "Processing...", true);
        (firebaseAuth.createUserWithEmailAndPassword(emailAddressToRegister.getText().toString(), passwordToRegister.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"You have successfully registered.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(RegisterActivity.this,  task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
