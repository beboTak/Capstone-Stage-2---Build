package com.udacity.befitness.room;

import android.os.AsyncTask;

class PopulateDbAsync extends AsyncTask<Void,Void,Void> {

    private final FitDoa mFitDao;
    PopulateDbAsync (FitnessRoomDatabase db){
      mFitDao=db.fitDoa();
    }
    @Override
    protected Void doInBackground(final Void... voids) {

        mFitDao.deleteAll();

        return null;
    }
}
