package com.example.finalproject_v2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {
    @Insert()
    void insert(Trip trip);

    @Insert
    void insertAll(Trip... trips);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trip_table")
    LiveData<List<Trip>> getAllTrips();

    @Update()
    void update(Trip trip);

}
