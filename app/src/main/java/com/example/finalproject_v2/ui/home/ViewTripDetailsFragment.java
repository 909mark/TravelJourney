package com.example.finalproject_v2.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.finalproject_v2.R;
import com.example.finalproject_v2.data.Response;
import com.example.finalproject_v2.data.Trip;
import com.example.finalproject_v2.data.WeatherAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewTripDetailsFragment extends Fragment {

    private TextView tripName;
    private TextView tripDestination;
    private TextView tripType;
    private TextView tripPrice;
    private ImageView tripImage;
    private TextView tripStartDate;
    private TextView tripEndDate;
    private TextView tripWeather;
    private RatingBar tripRating;
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private final String API_KEY = "fcd2c236893559071f53147e2c72132f";
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
        tripWeather = view.findViewById(R.id.detail_weather);
        tripStartDate = view.findViewById(R.id.detail_start_date);
        tripEndDate = view.findViewById(R.id.detail_end_date);
        tripRating = view.findViewById(R.id.detail_rating);
        tripImage = view.findViewById(R.id.detail_img);

        tripName.setText(trip.getName());
        tripDestination.setText(trip.getDestination());
        tripPrice.setText("Trip price (EUR):\t " + trip.getPrice());
        tripStartDate.setText("Start date:\t " + simpleDateFormat.format(trip.getStartDate()));
        tripEndDate.setText("End date: \t" + simpleDateFormat.format(trip.getEndDate()));
        tripRating.setRating(trip.getRating());
        tripWeather.setText("Retrieving weather information...");

        switch (trip.getType()) {
                case 0:
                    tripType.setText("Trip type: City break");
                    tripImage.setImageResource(R.drawable.city_landscape);
                    break;
                case 1:
                    tripType.setText("Trip type: Beach side");
                    tripImage.setImageResource(R.drawable.beach_landscape);
                    break;
                case 2:
                    tripType.setText("Trip type: Mountains");
                    tripImage.setImageResource(R.drawable.mountain_landscape);
                    break;
                default:
                    tripType.setText("Trip type: Unspecified");
                    tripImage.setBackgroundResource(R.drawable.ic_menu_camera);
                    break;
        }
        getWeather();
        return view;
    }

    private void getWeather() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<Response> responseCall =
                weatherAPI.getWeather(tripDestination.getText().toString(), API_KEY);
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String result = "Temperature: " +
                            Math.round(response.body().getMain().getTemp() - 272.15) +
                            "\u2103 - "
                            + response.body().getWeather().get(0).getDescription();
                    tripWeather.setText(result);
                } else {
                    if (response.code() == 404) {
                        tripWeather.setText("City not found in database");
                        Toast.makeText(getContext(), "City not found in database",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getContext(),"An error occurred, response code " +
                                response.code()+" ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                String errorMsg;
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    errorMsg = "Temperature: Destination not found";
                    tripWeather.setText(errorMsg);
                } else {
                    errorMsg = "No internet available";
                }
                tripWeather.setText(errorMsg);
            }
        });
    }

}
