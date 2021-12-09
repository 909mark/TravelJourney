package com.example.finalproject_v2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.finalproject_v2.R;
import com.example.finalproject_v2.data.Trip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ViewTripDetailsFragment extends Fragment {

    private TextView tripName;
    private TextView tripDestination;
    private CheckBox tripType;
    private TextView tripPrice;
    private ImageView tripImage;
    private TextView tripStartDate;
    private TextView tripEndDate;
    private RatingBar tripRating;
    DateFormat simpleDateFormat = SimpleDateFormat.getDateInstance();

    public ViewTripDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);

        Trip trip;
        Bundle args = this.getArguments();
        if (args != null) {
            trip = (Trip) args.getSerializable("Trip");
        } else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            return view;
        }

        tripName = view.findViewById(R.id.detail_name);
        tripDestination = view.findViewById(R.id.detail_destination);
        tripType = view.findViewById(R.id.detail_type);
        tripPrice = view.findViewById(R.id.detail_price);
        tripStartDate = view.findViewById(R.id.detail_start_date);
        tripEndDate = view.findViewById(R.id.detail_end_date);
        tripRating = view.findViewById(R.id.detail_rating);
        tripImage = view.findViewById(R.id.detail_img);

        tripName.setText(trip.getName());
        tripDestination.setText(trip.getDestination());
        tripType.setText(String.valueOf(trip.getType()));
        tripPrice.setText(String.valueOf(trip.getPrice()));
        tripStartDate.setText(simpleDateFormat.format(trip.getStartDate()));
        tripEndDate.setText(simpleDateFormat.format(trip.getEndDate()));
        tripRating.setRating(trip.getRating());

        switch (trip.getType()) {
                case 0:
                    tripImage.setImageResource(R.drawable.city_landscape);
                    break;
                case 1:
                    tripImage.setImageResource(R.drawable.beach_landscape);
                    break;
                case 2:
                    tripImage.setImageResource(R.drawable.mountain_landscape);
                    break;
                default:
                    tripImage.setBackgroundResource(R.drawable.ic_menu_camera);
                    break;
        }
        return view;

    }

}
