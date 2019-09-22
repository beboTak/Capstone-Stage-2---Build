package com.udacity.befitness.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.udacity.befitness.room.DataConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "workout_table")
public class Workout implements Parcelable {

     @NonNull
     @PrimaryKey
    public String mName;
    @TypeConverters(DataConverter.class)
    public List<Exercise> mExerciseList;

    public Workout(String name, List<Exercise> exerciseList) {
        mName = name;
        mExerciseList = exerciseList;
    }

    private Workout(Parcel source) {
        mName = source.readString();
        if (source.readByte() == 0x01) {
            mExerciseList = new ArrayList<Exercise>();
            source.readList(mExerciseList, Exercise.class.getClassLoader());
        } else {
            mExerciseList = null;
        }
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        if (mExerciseList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mExerciseList);
        }
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<Exercise> getmExerciseList() {
        return mExerciseList;
    }

    public void setmExerciseList(List<Exercise> mExerciseList) {
        this.mExerciseList = mExerciseList;
    }
}