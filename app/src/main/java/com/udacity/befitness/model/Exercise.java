package com.udacity.befitness.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.udacity.befitness.room.DataConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "exercise_table")
public class Exercise implements Parcelable {
   @PrimaryKey(autoGenerate = true)
   @NonNull
   public int mId;
    public String mName;
    public String mDescription;
    public int mExerciseCategory;
    @TypeConverters(DataConverter.class)
    public List<Integer> mMuscleList;
    @TypeConverters(DataConverter.class)
    public List<Integer> mEquipmentList;
    @TypeConverters(DataConverter.class)
    public List<String> mImageUrlList;

    public Exercise(int id, String name, String description, int exerciseCategory,
                    List<Integer> muscleList, List<Integer> equipmentList) {
        mId = id;
        mName = name;
        mDescription = description;
        mExerciseCategory = exerciseCategory;
        mMuscleList = muscleList;
        mEquipmentList = equipmentList;
        mImageUrlList = new ArrayList<>();
    }

    private Exercise(Parcel source) {
        mId = source.readInt();
        mName = source.readString();
        mDescription = source.readString();
        mExerciseCategory = source.readInt();
        if (source.readByte() == 0x01) {
            mMuscleList = new ArrayList<Integer>();
            source.readList(mMuscleList, Integer.class.getClassLoader());
        } else {
            mMuscleList = null;
        }
        if (source.readByte() == 0x01) {
            mEquipmentList = new ArrayList<Integer>();
            source.readList(mEquipmentList, Integer.class.getClassLoader());
        } else {
            mEquipmentList = null;
        }
        if (source.readByte() == 0x01) {
            mImageUrlList = new ArrayList<String>();
            source.readList(mImageUrlList, String.class.getClassLoader());
        } else {
            mImageUrlList = null;
        }
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel source) {
            return new Exercise(source);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeInt(mExerciseCategory);
        if (mMuscleList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mMuscleList);
        }
        if (mEquipmentList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mEquipmentList);
        }
        if (mImageUrlList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mImageUrlList);
        }
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmExerciseCategory() {
        return mExerciseCategory;
    }

    public void setmExerciseCategory(int mExerciseCategory) {
        this.mExerciseCategory = mExerciseCategory;
    }

    public List<Integer> getmMuscleList() {
        return mMuscleList;
    }

    public void setmMuscleList(List<Integer> mMuscleList) {
        this.mMuscleList = mMuscleList;
    }

    public List<Integer> getmEquipmentList() {
        return mEquipmentList;
    }

    public void setmEquipmentList(List<Integer> mEquipmentList) {
        this.mEquipmentList = mEquipmentList;
    }

    public List<String> getmImageUrlList() {
        return mImageUrlList;
    }

    public void setmImageUrlList(List<String> mImageUrlList) {
        this.mImageUrlList = mImageUrlList;
    }
}