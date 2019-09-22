package com.udacity.befitness.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.udacity.befitness.model.Exercise;
import com.udacity.befitness.model.Workout;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface FitDoa {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Workout workout);
    @Delete
    void delete(Workout workout);
    @Query("SELECT * FROM workout_table")
    LiveData<List<Workout>>getAllWorkout();
    @Query("DELETE  FROM workout_table")
    void deleteAll();
    @Query("SELECT mName FROM workout_table WHERE mName = :name LIMIT 1")
    LiveData<String> findItem(String name);



}
