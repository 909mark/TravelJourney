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
import android.widget.Toast;

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

public class EditTripDetailsFragment extends Fragment {

    private EditText tripName;
    private EditText tripDestination;
    private Slider tripPrice;
    private RatingBar tripRating;
    private TextView pickStartDate;
    private TextView pickEndDate;
    private Button saveButton;
    private Button deleteButton;
    private RadioButton radioButtonSeaside;
    private RadioButton radioButtonCityBreak;
    private RadioButton radioButtonMountain;
    DatePickerDialog.OnDateSetListener setListenerStart;
    DatePickerDialog.OnDateSetListener setListenerEnd;
    private TripViewModel tripViewModel;
    private Trip trip;
    DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    public EditTripDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_edit, container, false);

        Bundle args = this.getArguments();
        if (args != null) {
            trip = (Trip) args.getSerializable("Trip");
        } else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            return view;
        }

        initViews(view);
        setViews(trip);
        setupDatePicker();
        setListenerButton();

        return view;
    }

    void initViews(View view) {
        tripName = view.findViewById(R.id.edit_name);
        tripDestination = view.findViewById(R.id.edit_destination);
        tripPrice = view.findViewById(R.id.edit_price);
        tripRating = view.findViewById(R.id.edit_rating);
        saveButton = view.findViewById(R.id.edit_trip_save);
        deleteButton = view.findViewById(R.id.edit_trip_delete);
        pickStartDate = view.findViewById(R.id.edit_start_date);
        pickEndDate = view.findViewById(R.id.edit_end_date);
        radioButtonSeaside = view.findViewById(R.id.edit_type_seaside);
        radioButtonCityBreak = view.findViewById(R.id.edit_type_citybreak);
        radioButtonMountain = view.findViewById(R.id.edit_type_mountain);
    }

    void setViews(Trip trip) {
        tripName.setText(trip.getName());
        tripDestination.setText(trip.getDestination());
        tripPrice.setValue(trip.getPrice());
        tripRating.setRating(trip.getRating());
        pickStartDate.setText(simpleDateFormat.format(trip.getStartDate()));
        pickEndDate.setText(simpleDateFormat.format(trip.getEndDate()));
        if (trip.getType() == 0) radioButtonSeaside.setChecked(true);
        if (trip.getType() == 1) radioButtonSeaside.setChecked(true);
        if (trip.getType() == 2) radioButtonMountain.setChecked(true);
    }

    void setupDatePicker() {
        // TODO get year, month, day from trip
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

                Trip editedTrip = new Trip(trip.getId(), name, destination, tripType, price, startDate, endDate, rating);
                tripViewModel.update(editedTrip);
                Log.d("trip edited", editedTrip.toString());

                HomeFragment fragment = new HomeFragment();
                ((AppCompatActivity) v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_holder, fragment)
                        .commit();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            // TODO an are you sure? dialog
            @Override
            public void onClick(View v) {
                tripViewModel.delete(trip);

                HomeFragment fragment = new HomeFragment();
                ((AppCompatActivity) v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_holder, fragment)
                        .commit();
            }

        });
    }
}
