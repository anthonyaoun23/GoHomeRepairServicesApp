package com.goteam.gohomerepairservicesapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String TAG= "AdminActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    private void addService(String name, double rate){
        Service service=new Service(name,rate);

        database.getReference("Services").child(service.getServiceName()).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Failed to save user info", task.getException());
                    Toast.makeText(AdminActivity.this, "Failed to create new service. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(AdminActivity.this, "You have successfully created a new service.", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void  btnAddServiceClicked(View view){
        AlertDialog.Builder inputDialog= new AlertDialog.Builder(this);


        inputDialog.setTitle("New Service");

        inputDialog.setView(R.layout.service_text_entry);

        inputDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Dialog dialog= (Dialog) dialogInterface;
                EditText serviceInput=dialog.findViewById(R.id.serviceRegister);
                EditText rateInput=dialog.findViewById(R.id.rateRegister);
                String serviceName=serviceInput.getText().toString();
                Double rate=Double.valueOf(rateInput.getText().toString());
                System.out.print("test");

                addService(serviceName,rate);
            }
        });

        inputDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        inputDialog.show();

    }
}
