package com.example.finalproject_v2.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_v2.R;
import com.example.finalproject_v2.data.Trip;
import com.example.finalproject_v2.data.TripViewModel;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    List<Trip> trips;


    class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        private final TextView tripName;
        private final TextView tripDestination;
        private final CardView cardView;
        private final ImageView imageView;
        private final CheckBox checkBox;
        private TripViewModel tripViewModel;


        private TripViewHolder(View itemView)  {
            super(itemView);
            tripViewModel = new ViewModelProvider((ViewModelStoreOwner) itemView.getContext()).get(TripViewModel.class);
            checkBox = itemView.findViewById(R.id.checkbox);
            imageView = itemView.findViewById(R.id.tripImageView);
            tripName = itemView.findViewById(R.id.tripNameView);
            tripDestination = itemView.findViewById(R.id.tripDestinationView);
            cardView = itemView.findViewById(R.id.tripCardView);
            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trips.get(getAdapterPosition()).setFav(checkBox.isChecked());
                    tripViewModel.update(trips.get(getAdapterPosition()));
                }
            });
        }

        public TextView getTripName() {
            return tripName;
        }
        public TextView getTripDestination() {
            return tripDestination;
        }
        public CardView getCardView() { return cardView; }
        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public void onClick(View v) {
            ViewTripDetailsFragment fragment = new ViewTripDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Trip", trips.get(getAdapterPosition()));
            fragment.setArguments(bundle);
            FragmentManager fm = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
            fm.beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.fragment_holder, fragment)
                    .commit();
        }

        @Override
        public boolean onLongClick(View v) {
            EditTripDetailsFragment fragment = new EditTripDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Trip", trips.get(getAdapterPosition()));
            fragment.setArguments(bundle);
            FragmentManager fm = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
            fm.beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.fragment_holder, fragment)
                    .commit();
            return true;
        }
    }

    public TripAdapter() { }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_layout, parent, false);
        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        if (trips != null) {

            Trip currentTrip = trips.get(position);
            holder.checkBox.setChecked(trips.get(position).isFav());
            holder.getTripName().setText(currentTrip.getName());
            holder.getTripDestination().setText(currentTrip.getDestination());
            switch (trips.get(position).getType()) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.city_landscape);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.beach_landscape);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.mountain_landscape);
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.ic_menu_camera);
                    break;
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.getTripName().setText(R.string.name_not_set);
            holder.getTripDestination().setText(R.string.destination_not_set);
        }
    }

    public List<Trip> getTrips() {
        return trips;
    }

    void setTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (trips != null)
            return trips.size();
        else return 0;
    }
}
