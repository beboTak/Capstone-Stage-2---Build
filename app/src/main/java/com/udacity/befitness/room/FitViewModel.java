package com.udacity.befitness.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.befitness.model.Exercise;
import com.udacity.befitness.model.Workout;
import com.udacity.befitness.utils.ExerciseRepository;

import java.util.ArrayList;
import java.util.List;

public class FitViewModel extends AndroidViewModel {

    private String name;
    private ExerciseRepository mExerciseRepository;
    private LiveData<List<Workout>>mAllWorkout;
    private LiveData<String> mWorkout;


    public FitViewModel(@NonNull Application application) {
        super(application);
        mExerciseRepository=new ExerciseRepository(application);
        mAllWorkout=mExerciseRepository.getAllWorkout();
        mWorkout=mExerciseRepository.findItem(name);

    }
    public LiveData<List<Workout>>getmAllWorkout(){
        return mAllWorkout;
    }
    public void insert(Workout workout)
    {
        mExerciseRepository.insert(workout);
    }
    public void delete(Workout workout)
    {
        mExerciseRepository.delete(workout);
    }
    public LiveData<String> findItem(String name){return mWorkout;}
}
