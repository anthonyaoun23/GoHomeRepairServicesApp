package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceProviderInfoActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String uid;

    private EditText address;
    private EditText company_name;
    private EditText phone_number;
    private CheckBox licensed;
    private EditText description;
    private Button done_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_info);

        address=findViewById(R.id.sp_address);
        company_name=findViewById(R.id.sp_company_name);
        phone_number=findViewById(R.id.sp_phone_number);
        licensed=findViewById(R.id.sp_licensed);
        done_button=findViewById(R.id.sp_done_account_info);
        description=findViewById(R.id.sp_description);
        uid=firebaseUser.getUid();
    }

    private boolean registerSimpleVerification(String iaddress, String icompany_name, String inumber ) {
        if (iaddress.isEmpty()) {
           address.setError("Address is required");
           address.requestFocus();
           return false;
        }


        if (!inumber.matches("^[0-9]{10}$")) {
            phone_number.setError("Please enter a valid phone number. Only numbers. No dashes.");
            phone_number.requestFocus();
            return false;
        }

        if (icompany_name.isEmpty()) {
            company_name.setError("Company name is required");
            company_name.requestFocus();
            return false;
        }

        return true;
    }

    public void btnDoneClicked(View view){
        ServiceProvider user= new ServiceProvider();
        final String scompany_name= company_name.getText().toString().trim();
        final String snumber=phone_number.getText().toString().trim();
        final String saddress=address.getText().toString().trim();
        final String sdescription=description.getText().toString().trim();

        user.setAddress(saddress);
        user.setCompanyName(scompany_name);
        user.setPhoneNumber(snumber);
        user.setDescription(sdescription);


        if(!registerSimpleVerification(saddress,scompany_name,snumber)){
            return;
        }else{
            database.getReference().child("Users").child(uid).setValue(user);
            database.getReference().child("Users").child(uid).child("account_finalized").setValue(true);
            Intent intent = new Intent(ServiceProviderInfoActivity.this, ServiceProviderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            return;
        }

    }


}
