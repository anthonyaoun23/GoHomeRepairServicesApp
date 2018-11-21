package com.goteam.gohomerepairservicesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ProviderInfo extends AppCompatActivity {
    private EditText address;
    private EditText phoneNumber;
    private EditText description;
    private EditText companyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_info);
        address=findViewById(R.id.sp_address);
        address=findViewById(R.id.sp_address);
        address=findViewById(R.id.sp_address);
        companyName=findViewById(R.id.sp_company_name);
    }
}
