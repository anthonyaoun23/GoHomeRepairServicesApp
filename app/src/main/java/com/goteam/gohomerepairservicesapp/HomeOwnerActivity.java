package com.goteam.gohomerepairservicesapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.LinkedList;

public class HomeOwnerActivity extends AppCompatActivity {
    private static final String TAG = "HomeownerActivity";
    LinkedList<ServiceProvider> serviceProviders;
    LinkedList<ServiceProvider> resultServiceProviders;
    LinkedList<String> services;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private HO_SPAdapter spRecyclerAdapter;
    private Spinner servicesSpinner;
    private Spinner ratingsSpinner;
    private Button selectDateButton;
    private Button selectTimeButton;
    private TextView timeTextView;
    private ArrayAdapter<String> servicesAdapter;
    private ArrayAdapter<Integer> ratingsAdapter;
    private LocalDateTime selectedDateTime;
    private RadioGroup searchType;
    private RecyclerView providerRecycler;
    private FirebaseUser firebaseUser;
    private String uid;
    private Homeowner homeowner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidThreeTen.init(this);

        selectedDateTime = LocalDateTime.now();

        setContentView(R.layout.activity_home_owner);



        //Load user
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        //Load current services owned by the sp
        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                homeowner = dataSnapshot.child(uid).getValue(Homeowner.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeOwnerActivity.this, "Canceled", Toast.LENGTH_SHORT).show();

            }
        });





        providerRecycler = findViewById(R.id.provider_recycler);
        searchType = findViewById(R.id.searchType);
        servicesSpinner = findViewById(R.id.servicesSpinner);
        ratingsSpinner = findViewById(R.id.ratingsSpinner);
        selectDateButton = findViewById(R.id.selectDateButton);
        selectTimeButton = findViewById(R.id.selectTimeButton);
        timeTextView = findViewById(R.id.timeTextView);

        serviceProviders = new LinkedList<>();
        resultServiceProviders = new LinkedList<>();
        services = new LinkedList<>();

        spRecyclerAdapter = new HO_SPAdapter(resultServiceProviders);

        providerRecycler.addItemDecoration(new DividerItemDecoration(providerRecycler.getContext(), DividerItemDecoration.VERTICAL));
        providerRecycler.setHasFixedSize(true);
        providerRecycler.setLayoutManager(new LinearLayoutManager(this));
        providerRecycler.setAdapter(spRecyclerAdapter);

        //Setting up recyclerView

        servicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(servicesAdapter);
        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                resultServiceProviders.clear();
                String item = servicesAdapter.getItem(i);

                if (i > 0 && item != null)
                    for (ServiceProvider provider : serviceProviders)
                        for (Service service : provider.getServices())
                            if (service.getServiceName() != null && service.getServiceName().toLowerCase().contains(item.toLowerCase()))
                                resultServiceProviders.add(provider);

                spRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                resultServiceProviders.clear();
            }
        });

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HomeOwnerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        selectedDateTime = LocalDateTime.of(LocalDate.of(year, month + 1, dayOfMonth), selectedDateTime.toLocalTime());
                        updateDateTime();
                    }
                }, selectedDateTime.getYear(), selectedDateTime.getMonthValue() - 1, selectedDateTime.getDayOfMonth()).show();
            }
        });

        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(HomeOwnerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        selectedDateTime = LocalDateTime.of(selectedDateTime.toLocalDate(), LocalTime.of(hourOfDay, minute));
                        updateDateTime();
                    }
                }, selectedDateTime.getHour(), selectedDateTime.getMinute(), true).show();
            }
        });

        ratingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new Integer[]{0, 1, 2, 3, 4, 5});
        ratingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingsSpinner.setAdapter(ratingsAdapter);
        ratingsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                resultServiceProviders.clear();
                Integer item = ratingsAdapter.getItem(i);

                if (item != null)
                    for (ServiceProvider provider : serviceProviders)
                        if (provider.getRating() >= item)
                            resultServiceProviders.add(provider);

                spRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                resultServiceProviders.clear();
            }
        });

        searchType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                AdapterView.OnItemSelectedListener listener;

                switch (id) {
                    case R.id.radioService:
                        servicesSpinner.setVisibility(View.VISIBLE);
                        ratingsSpinner.setVisibility(View.GONE);
                        selectDateButton.setVisibility(View.GONE);
                        selectTimeButton.setVisibility(View.GONE);
                        timeTextView.setVisibility(View.GONE);

                        listener = servicesSpinner.getOnItemSelectedListener();

                        if (listener != null)
                            listener.onItemSelected(null, servicesSpinner.getSelectedView(), servicesSpinner.getSelectedItemPosition(), servicesSpinner.getSelectedItemId());

                        break;
                    case R.id.radioTime:
                        servicesSpinner.setVisibility(View.GONE);
                        ratingsSpinner.setVisibility(View.GONE);
                        selectDateButton.setVisibility(View.VISIBLE);
                        selectTimeButton.setVisibility(View.VISIBLE);
                        timeTextView.setVisibility(View.VISIBLE);

                        break;
                    case R.id.radioRating:
                        servicesSpinner.setVisibility(View.GONE);
                        ratingsSpinner.setVisibility(View.VISIBLE);
                        selectDateButton.setVisibility(View.GONE);
                        selectTimeButton.setVisibility(View.GONE);
                        timeTextView.setVisibility(View.GONE);

                        listener = ratingsSpinner.getOnItemSelectedListener();

                        if (listener != null)
                            listener.onItemSelected(null, ratingsSpinner.getSelectedView(), ratingsSpinner.getSelectedItemPosition(), ratingsSpinner.getSelectedItemId());

                        break;
                }
            }
        });

        setListeners();
        fetchFromDatabase();
        updateDateTime();
    }

    private void setListeners() {
        spRecyclerAdapter.setOnCardClick(new HO_SPAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("homeowner", homeowner);
                bundle.putSerializable("Provider",  resultServiceProviders.get(position));
                Intent intent = new Intent(HomeOwnerActivity.this, BookedServiceItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void fetchFromDatabase() {
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesAdapter.add("Select a service...");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Service service = ds.getValue(Service.class);

                    if (service == null)
                        continue;

                    String name = service.getServiceName();

                    if (name == null)
                        continue;

                    services.add(name);
                }

                servicesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if ("provider".equals(user.child("role").getValue())) {
                        serviceProviders.add(user.getValue(ServiceProvider.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    public void btnBookingsClicked(View view) {
        //startActivity(new Intent(this, MyBookingsActivity.class));
    }
        private void updateDateTime() {
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);
            timeTextView.setText(selectedDateTime.format(dtf));

            if (searchType.getCheckedRadioButtonId() != R.id.radioTime)
                return;

            resultServiceProviders.clear();

            for (ServiceProvider provider : serviceProviders) {
                for (TimeOfAvailability avail : provider.getAvailabilities().values()) {
                    LocalDateTime start = LocalDateTime.of(avail.getYear(), avail.getMonth(), avail.getDay(), avail.getHourStart(), avail.getMinuteStart());
                    LocalDateTime end = LocalDateTime.of(avail.getYear(), avail.getMonth(), avail.getDay(), avail.getHourEnd(), avail.getMinuteEnd());

                    if (selectedDateTime.isAfter(start) && selectedDateTime.isBefore(end))
                        resultServiceProviders.add(provider);
                }
            }

            spRecyclerAdapter.notifyDataSetChanged();

        }
    }

