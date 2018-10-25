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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText emailToLogin;
    private EditText passwordToLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailToLogin = findViewById(R.id.emailAddressLogin);
        passwordToLogin = findViewById(R.id.passwordLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void btnLoginUserClicked(View view){
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Please wait...", "Processing...", true);

        firebaseAuth.signInWithEmailAndPassword(emailToLogin.getText().toString(),passwordToLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(!task.isSuccessful() || user == null) {
                    Log.e(TAG, "Failed to sign in", task.getException());
                    Toast.makeText(LoginActivity.this, "Failed to sign in. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

                //Toast.makeText(LoginActivity.this, "You have successfully logged in.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });
    }

}
