package com.example.finalproject_v2.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_v2.R;
import com.example.finalproject_v2.data.Trip;
import com.example.finalproject_v2.data.TripViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewTrips;
    private TripViewModel tripViewModel;
    private TripAdapter tripAdapter;
    private FloatingActionButton addTripButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewTrips = root.findViewById(R.id.recyclerView);
        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(root.getContext()));
        tripAdapter = new TripAdapter();
        recyclerViewTrips.setAdapter(tripAdapter);
        // Add an observer on the LiveData returned by getAllTrips() function
        // The onChanged() method fires when the observed data changes and the activity(?) is
        // in the foreground.
        tripViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                // update the cached copy
                tripAdapter.setTrips(trips);
                Log.d("DATABASE", "onChanged triggered");
                for (Trip trip : trips) {
                    Log.d("DATABASE", trip.toString());
                }
            }
        });

        addTripButton = root.findViewById(R.id.fab);
        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTripDetailsFragment fragment = new AddTripDetailsFragment();
                ((AppCompatActivity) v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_holder, fragment)
                        .addToBackStack("")
                        .commit();
            }
        });

        return root;
    }


}
