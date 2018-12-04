package com.goteam.gohomerepairservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;


public class ServiceRatingActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button addRatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rating);

        ratingBar = findViewById(R.id.ratingBar);
        addRatingButton = findViewById(R.id.button);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                Toast.makeText(getApplicationContext(),"Your Selected Ratings  : " + String.valueOf(rating),Toast.LENGTH_LONG).show();

            }
        });

        addRatingButton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {
                float rating=ratingBar.getRating();
                Toast.makeText(getApplicationContext(),"Your Selected Ratings  : " + String.valueOf(rating),Toast.LENGTH_LONG).show();
                startNewIntent(rating);


            }
        });
    }

    public void startNewIntent(float rating){
        Intent intent = new Intent(this, BookedServiceItemActivity.class);
        intent.putExtra("rating",rating);
        startActivity(intent);
    }
}
