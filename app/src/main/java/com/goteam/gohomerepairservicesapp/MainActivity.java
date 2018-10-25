package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){
            // TODO User already logged in
        }

    }

    public void btnRegisterClicked(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

    public void btnLoginClicked(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

}
