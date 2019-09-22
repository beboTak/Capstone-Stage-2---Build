package com.udacity.befitness.utils;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.udacity.befitness.model.Workout;
import com.udacity.befitness.room.FitDoa;
import com.udacity.befitness.room.FitnessRoomDatabase;


import java.util.List;


public class ExerciseRepository {
    private String name;
    private FitDoa mFitDao;
    private LiveData<List<Workout>>mAllWorkout;
    private LiveData<String> mWorkout;

    public ExerciseRepository(Application application){
        FitnessRoomDatabase database=FitnessRoomDatabase.getDatabase(application);
        mFitDao=database.fitDoa();
        mAllWorkout=mFitDao.getAllWorkout();
        mWorkout=mFitDao.findItem(name);
    }
    public LiveData<List<Workout>>getAllWorkout(){
        return mAllWorkout;
    }
    public void insert(Workout workout)
    {
        new insertAsyncTask(mFitDao).execute(workout);
    }
    public void delete(Workout workout)
    {
        new deleteAsyncTask(mFitDao).execute(workout);
    }
    public LiveData<String> findItem(String name)
    {
        return mWorkout;
    }
    private static class insertAsyncTask extends AsyncTask<Workout,Void,Void>
    {
        private FitDoa mAsyncTaskFitDao;
        insertAsyncTask(FitDoa doa){
            mAsyncTaskFitDao=doa;

        }


        @Override
        protected Void doInBackground(final Workout... param) {
            mAsyncTaskFitDao.insert(param[0]);
            return null;
        }


    }
    private static class deleteAsyncTask extends AsyncTask<Workout,Void,Void>
    {
        private FitDoa mAsyncTaskFitDao;
        deleteAsyncTask(FitDoa doa){
            mAsyncTaskFitDao=doa;

        }


        @Override
        protected Void doInBackground(final Workout... param) {
            mAsyncTaskFitDao.delete(param[0]);
            return null;
        }
    }

}
