package com.goteam.gohomerepairservicesapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private int mYear, mMonth, mDay, mHourStart, mMinuteStart, mHourEnd, mMinuteEnd;

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
            final Calendar c = Calendar.getInstance();
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
            final Calendar c = Calendar.getInstance();
            mHourStart = c.get(Calendar.HOUR_OF_DAY);
            mMinuteStart = c.get(Calendar.MINUTE);

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
            final Calendar c = Calendar.getInstance();
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


        }
    }

}
