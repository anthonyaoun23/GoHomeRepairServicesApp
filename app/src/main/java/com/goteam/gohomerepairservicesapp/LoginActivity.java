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

public class LoginActivity extends AppCompatActivity {

    private EditText emailToLogin;
    private EditText passwordToLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailToLogin = (EditText) findViewById(R.id.emailAddressLogin);
        passwordToLogin = (EditText) findViewById(R.id.passwordLogin);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void btnLoginUserClicked(View view){
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Please wait...", "Processing...", true);

        firebaseAuth.signInWithEmailAndPassword(emailToLogin.getText().toString(),passwordToLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "You have successfully logged in.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Email",firebaseAuth.getCurrentUser().getEmail());
                    startActivity(intent);
                } else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });


    }
}
