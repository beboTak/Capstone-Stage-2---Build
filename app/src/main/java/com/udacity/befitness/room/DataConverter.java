package com.udacity.befitness.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.befitness.model.Exercise;
import com.udacity.befitness.model.Workout;

import java.io.Serializable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataConverter implements Serializable {
    @TypeConverter
    public static String fromWorkoutValuesList(List<Exercise> workouts)
    {
        if (workouts==null)
        {
            return (null);
        }
        Gson gson=new Gson();
        Type type = new TypeToken<List<Exercise>>() {
        }.getType();
        String json =gson.toJson(workouts,type);

        return json;
    }
    @TypeConverter
    public static List<Exercise>toWorkoutList(String workoutValusString){
        if (workoutValusString==null)
        {
            return (null);
        }
        Gson gson=new Gson();
        Type type=new TypeToken<List<Exercise>>(){

        }.getType();
        List<Exercise> workoutArrayList =gson.fromJson(workoutValusString,type);
        return workoutArrayList;
    }
    @TypeConverter
    public static List<String>fromString(String value){
        Gson gson=new Gson();
        Type type=new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(value,type);

    }
    @TypeConverter
    public static String fromArrayLisr(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static List<Integer> gettingListFromString(String genreIds) {
        List<Integer> list = new ArrayList<>();
        String[] array = genreIds.split(",");
        for(String s: array){
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    @TypeConverter
    public  String writingStringFromList(List<Integer> list) {
        String genreIds = "";
        for(int i : list){
            genreIds += ","+i;
        }
        return genreIds;
    }
}
