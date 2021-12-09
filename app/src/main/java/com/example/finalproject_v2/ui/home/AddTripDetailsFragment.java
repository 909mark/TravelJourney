package com.example.finalproject_v2.ui.home;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject_v2.R;
import com.example.finalproject_v2.data.Trip;
import com.example.finalproject_v2.data.TripViewModel;
import com.google.android.material.slider.Slider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTripDetailsFragment extends Fragment {

    private EditText tripName;
    private EditText tripDestination;
    private RatingBar tripRating;
    private TextView pickStartDate;
    private TextView pickEndDate;
    private Button saveButton;
    private Slider tripPrice;
    private RadioButton radioButtonSeaside;
    private RadioButton radioButtonCityBreak;
    private RadioButton radioButtonMountain;
    DatePickerDialog.OnDateSetListener setListenerStart;
    DatePickerDialog.OnDateSetListener setListenerEnd;
    private TripViewModel tripViewModel;
    public AddTripDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_add, container, false);


        initViews(view);
        setupDatePicker();
        setListenerButton();

        return  view;
    }

    void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        pickStartDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,
                    setListenerStart, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        pickEndDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,
                    setListenerEnd, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        setListenerStart = (view1, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            String date = dayOfMonth + "/" + month1 + "/" + year1;
            pickStartDate.setText(date);
        };

        setListenerEnd = (view1, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            String date = dayOfMonth + "/" + month1 + "/" + year1;
            pickEndDate.setText(date);
        };
    }

    void initViews(View view) {
        tripPrice= view.findViewById(R.id.add_price);
        pickStartDate = view.findViewById(R.id.add_start_date);
        pickEndDate = view.findViewById(R.id.add_end_date);
        tripName = view.findViewById(R.id.add_name);
        tripDestination = view.findViewById(R.id.add_destination);
        tripRating = view.findViewById(R.id.add_rating);
        saveButton = view.findViewById(R.id.add_trip_save);
        radioButtonSeaside = view.findViewById(R.id.add_type_seaside);
        radioButtonCityBreak = view.findViewById(R.id.add_type_citybreak);
        radioButtonMountain = view.findViewById(R.id.add_type_mountain);
    }

    void setListenerButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tripType;
                int price = Math.round(tripPrice.getValue());
                tripType = radioButtonCityBreak.isChecked() ? 0 : (radioButtonSeaside.isChecked() ? 1 : (radioButtonMountain.isChecked() ? 2 : -1));
                String name = tripName.getText().toString().isEmpty() ? "No trip name set" : tripName.getText().toString();
                String destination = tripDestination.getText().toString().isEmpty() ? "No trip destination set" : tripDestination.getText().toString();
                float rating = tripRating.getRating();
                DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date startDate, endDate;
                try {
                    startDate = simpleDateFormat.parse(pickStartDate.getText().toString());
                    endDate = simpleDateFormat.parse(pickEndDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    startDate = Calendar.getInstance().getTime();
                    endDate = Calendar.getInstance().getTime();
                }

                Trip trip = new Trip(0, name, destination, tripType, price, startDate, endDate, rating);
                tripViewModel.insert(trip);
                Log.d("new trip inserted", trip.toString());

                HomeFragment fragment = new HomeFragment();
                ((AppCompatActivity) v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_holder, fragment)
                        .commit();
            }
        });
    }

}
