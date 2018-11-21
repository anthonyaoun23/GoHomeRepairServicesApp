package com.goteam.gohomerepairservicesapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


public class AvailabilitySelectorActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker, btnTimePickerStart, btnTimePickerEnd, submitBtn;
    EditText txtDate, txtTimeStart, txtTimeEnd;
    private Calendar c, timeStart, timeEnd;
    private int mYear, mMonth, mDay, mHourStart, mMinuteStart, mHourEnd, mMinuteEnd;
    private TimeOfAvailability availabilitySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_selector);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePickerStart=(Button)findViewById(R.id.btn_time_start);
        btnTimePickerEnd=(Button)findViewById(R.id.btn_time_end);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTimeStart=(EditText)findViewById(R.id.in_time_start);
        txtTimeEnd=(EditText)findViewById(R.id.in_time_end);

        submitBtn = (Button)findViewById(R.id.submitBtn);


        btnDatePicker.setOnClickListener(this);
        btnTimePickerStart.setOnClickListener(this);
        btnTimePickerEnd.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnTimePickerStart) {

            // Get Current Time
            timeStart = Calendar.getInstance();
            mHourStart = timeStart.get(Calendar.HOUR_OF_DAY);
            mMinuteStart = timeStart.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimeStart.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourStart, mMinuteStart, false);
            timePickerDialog.show();
        }

        if (v == btnTimePickerEnd) {

            // Get Current Time
            timeStart = Calendar.getInstance();
            mHourEnd = c.get(Calendar.HOUR_OF_DAY);
            mMinuteEnd = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimeEnd.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourEnd, mMinuteEnd, false);
            timePickerDialog.show();
        }

        if(v == submitBtn){
            if(timeVerification()){
                availabilitySelected = new TimeOfAvailability(mYear,mMonth,mDay,mHourStart,mMinuteStart,mHourEnd,mMinuteEnd);
                Intent intent = new Intent(this, ServiceProviderActivity.class);
                intent.putExtra("date",availabilitySelected.getArray());
                startActivity(intent);
            }
        }
    }

    public boolean timeVerification(){

        // Get Current Date
        Calendar currentDate = Calendar.getInstance();

        // Check if day chosen is in the future (or current)
        if(currentDate.compareTo(c)<0){
            txtDate.setError("Please select a valid date.");
            txtDate.requestFocus();
            return false;
        }

        // Check if start time is before the end time
        if(timeStart.compareTo(timeEnd)>=0){
            txtTimeEnd.setError("Please select a valid time.");
            txtTimeEnd.requestFocus();
            return false;
        }

        return true;
    }

}
