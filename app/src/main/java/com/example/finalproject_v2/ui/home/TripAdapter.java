package com.example.finalproject_v2.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_v2.R;
import com.example.finalproject_v2.data.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    List<Trip> trips;

    class TripViewHolder extends RecyclerView.ViewHolder  {
        private final TextView tripName;
        private final TextView tripDestination;
        private final CardView cardView;
        private final ImageView imageView;
        private final CheckBox checkBox;

        private TripViewHolder(View itemView)  {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            imageView = itemView.findViewById(R.id.tripImageView);
            tripName = itemView.findViewById(R.id.tripNameView);
            tripDestination = itemView.findViewById(R.id.tripDestinationView);
            cardView = itemView.findViewById(R.id.tripCardView);
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
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.checkBox.setChecked(true);
                }
            });
            Trip currentTrip = trips.get(position);
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
