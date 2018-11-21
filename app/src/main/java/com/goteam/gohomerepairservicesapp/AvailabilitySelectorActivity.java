package com.goteam.gohomerepairservicesapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AvailabilitySelectorActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker, btnTimePickerStart, btnTimePickerEnd, submitBtn;
    EditText txtDate, txtTimeStart, txtTimeEnd;
    private LocalDate date;
    private LocalTime startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidThreeTen.init(this); // required for proper functioning of date/time stuff

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_selector);

        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePickerStart = findViewById(R.id.btn_time_start);
        btnTimePickerEnd = findViewById(R.id.btn_time_end);
        txtDate = findViewById(R.id.in_date);
        txtTimeStart = findViewById(R.id.in_time_start);
        txtTimeEnd = findViewById(R.id.in_time_end);

        submitBtn = findViewById(R.id.submitBtn);


        btnDatePicker.setOnClickListener(this);
        btnTimePickerStart.setOnClickListener(this);
        btnTimePickerEnd.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        date = LocalDate.now();
        startTime = LocalTime.now();
        endTime = LocalTime.now().plusHours(1);

    }

    @Override
    public void onClick(View v) {
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);

        if (v == btnDatePicker) {
            txtDate.setError(null);//removes error
            txtDate.clearFocus();    //clear focus from edittext

            // Get Current Date

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                            txtDate.setText(date.format(dateFormat));

                        }
                    }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());


            datePickerDialog.show();
        }

        if (v == btnTimePickerStart) {

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            startTime = LocalTime.of(hourOfDay, minute);
                            txtTimeStart.setText(startTime.format(timeFormat));
                        }
                    }, startTime.getHour(), startTime.getMinute(), true);
            timePickerDialog.show();
        }

        if (v == btnTimePickerEnd) {
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            endTime = LocalTime.of(hourOfDay, minute);
                            txtTimeEnd.setText(endTime.format(timeFormat));
                        }
                    }, endTime.getHour(), endTime.getMinute(), true);
            timePickerDialog.show();
        }

        if (v == submitBtn) {
            if (timeVerification()) {
                TimeOfAvailability availabilitySelected = new TimeOfAvailability(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), startTime.getHour(), startTime.getMinute(), endTime.getHour(), endTime.getMinute());
                Intent intent = new Intent(this, ServiceProviderActivity.class);
                intent.putExtra("date", availabilitySelected.getArray());
                startActivity(intent);
            }
        }
    }

    public boolean timeVerification() {

        // Get Current Date
        if (date == null)
            return false;

        LocalDateTime currentDate = LocalDateTime.now();

        // Check if day chosen is in the future (or current)
        if (currentDate.isAfter(LocalDateTime.of(date, startTime))) {
            Toast.makeText(this, "Please select a valid date.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if start time is before the end time
        if (startTime == null || endTime == null || startTime.isAfter(endTime)) {

            Toast.makeText(this, "Please select a valid time.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
