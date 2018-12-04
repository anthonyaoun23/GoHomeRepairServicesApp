package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class HomeOwnerActivity extends AppCompatActivity {

    EditText searchText;
    LinkedList<ServiceProvider> serviceProviders;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");
    private FirebaseAuth firebaseAuth;
    private RadioGroup searchType;
    private RecyclerView providerRecycler;
    private HO_SPAdapter spRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceProviders = new LinkedList<>();
        setContentView(R.layout.activity_home_owner);
        searchText = findViewById(R.id.search_input);
        searchType = findViewById(R.id.searchType);
        providerRecycler=findViewById(R.id.provider_recycler);

        //Setting up recyclerView
        spRecyclerAdapter = new HO_SPAdapter(serviceProviders);
        layoutManager=new LinearLayoutManager(this);
        providerRecycler.setHasFixedSize(true);
        providerRecycler.setLayoutManager(layoutManager);
        providerRecycler.setAdapter(spRecyclerAdapter);

        setListeners();
        loadServiceProviders();
    }

    private void setListeners()

    {

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener()

        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchProvider(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        spRecyclerAdapter.setOnCardClick(new HO_SPAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              //  Intent intent=new Intent(HomeOwnerActivity.this, );
            }
        });

    }

    private void loadServiceProviders() {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if ((user.child("role").getValue()).equals("provider")) {
                        serviceProviders.add(user.getValue(ServiceProvider.class));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void searchProvider(String text){
        text=text.trim();
        if(text==null||text.equals("")){
            Toast.makeText(HomeOwnerActivity.this, "Please enter a valid search", Toast.LENGTH_LONG).show();
            return ;
        }

        int selectedButton = searchType.getCheckedRadioButtonId();

        switch(selectedButton){

            case(R.id.radioService):
                for(ServiceProvider provider : serviceProviders){
                    for(Service service : provider.getServices()){
                        if(service.getServiceName().contains(text)){
                            addProvider(provider);
                        }
                    }
                }


            case(R.id.radioRating):
                if(!text.matches("[0-5].?[0-9]?")){
                    Toast.makeText(HomeOwnerActivity.this, "Please enter a rating between 0 and 5", Toast.LENGTH_LONG).show();
                    return ;
                }
                for(ServiceProvider provider : serviceProviders){
                        if(provider.getRating()==Double.valueOf(text)){
                            addProvider(provider);
                        }
                }



            case(R.id.radioTime):
                //TO IMPLEMENT BY NIC
        }
    }


    private void addProvider(ServiceProvider provider){
        serviceProviders.add(provider);

    }

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}



