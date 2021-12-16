package com.example.finalproject_v2.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "trip_table")
public class Trip implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final int id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "destination")
    private final String destination;

    @ColumnInfo(name = "type")
    private final int type;

    @ColumnInfo(name = "price")
    private final int price;

    @ColumnInfo(name = "start_date")
    @TypeConverters(DateConverter.class)
    private final Date startDate;

    @ColumnInfo(name = "end_date")
    @TypeConverters(DateConverter.class)
    private final Date endDate;

    @ColumnInfo(name = "rating")
    private final float rating;

    @ColumnInfo(name = "fav")
    boolean fav;

    public Trip(int id, String name, String destination, int type, int price,
                Date startDate, Date endDate,
                float rating, boolean fav) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public int getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getRating() {
        return rating;
    }

    public boolean isFav() { return fav; }

    public void setFav(boolean fav) { this.fav = fav; }

    public static Trip[] getSampleTrips() {
        Trip[] trips = new Trip[]{

                new Trip(1, "London trip", "London, UK", 1, 2500, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 4, false),
                new Trip(2, "Cluj trip", "Cluj-Napoca, RO", 0, 2000, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 4, false),
                new Trip(3, "Maldive holiday", "Maldives, MV", 2, 4500, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 5, false),
                new Trip(4, "London trip", "London, UK", 1, 2500, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 4, false),
                new Trip(5, "Cluj trip", "Cluj-Napoca, RO", 0, 2000, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 4, false),
                new Trip(6, "Maldive holiday", "Maldives, MV", 2, 4500, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 5, false),
                new Trip(7, "London trip", "London, UK", 1, 2500, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 4, false),
                new Trip(8, "Cluj trip", "Cluj-Napoca, RO", 0, 2000, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 4, false),
                new Trip(9, "Maldive holiday", "Maldives, MV", 2, 4500, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 5, false)
        };
        return trips;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", rating=" + rating +
                ", fav=" + fav +
                '}';
    }
}
