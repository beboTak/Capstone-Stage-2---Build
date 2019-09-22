package com.udacity.befitness.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.udacity.befitness.model.Exercise;
import com.udacity.befitness.model.Workout;

@Database(entities = {Workout.class, Exercise.class},version = 1, exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class FitnessRoomDatabase extends RoomDatabase {
    public abstract FitDoa fitDoa();
    private static volatile FitnessRoomDatabase INSTANCE;
   public static FitnessRoomDatabase getDatabase(final Context context)
    {
        synchronized (FitnessRoomDatabase.class){
            if (INSTANCE==null)
            {
               INSTANCE= Room.databaseBuilder(context.getApplicationContext(),FitnessRoomDatabase.class,"exercise_database")
                       .allowMainThreadQueries()
                       .addCallback(sRoomDatabaseCallback)
                       .build();
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);

                   // new PopulateDbAsync(INSTANCE).execute();

                }
            };
}
