package com.udacity.befitness;


import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.google.android.gms.dynamic.IFragmentWrapper;
import com.udacity.befitness.model.Exercise;
import com.udacity.befitness.model.Workout;
import com.udacity.befitness.room.FitViewModel;
import com.udacity.befitness.views.ExerciseListFragment;


import java.io.Serializable;
import java.util.List;


public class WorkoutDetailActivity extends AppCompatActivity {

    private FitViewModel mFitViewModel;

    private MenuItem saveButton;
    private MenuItem unsaveButton;
    public static int saved;
    public static String workoutName;
    private Workout workoutdone;
    private String mWorkoutName;
    private List<Exercise> mExerciseList;

    private ExerciseListFragment mExerciseListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        mFitViewModel = ViewModelProviders.of(this).get(FitViewModel.class);
        mWorkoutName = getIntent().getStringExtra(getString(R.string.workout_name_extra));
        mExerciseList =
                getIntent().getParcelableArrayListExtra(getString(R.string.exercise_list_extra));

        getSupportActionBar().setTitle(mWorkoutName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // Toast.makeText(this,mExerciseList.get(0).mDescription,Toast.LENGTH_SHORT).show();
        if (savedInstanceState == null) {
            mExerciseListFragment = new ExerciseListFragment();
            mExerciseListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.workout_detail_activity, mExerciseListFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.workout_detail_settings, menu);
        saveButton = menu.findItem(R.id.settings_save_workout);
        unsaveButton = menu.findItem(R.id.settings_unsave_workout);
        if (isSaved()) {
            saveButton.setVisible(false);
            unsaveButton.setVisible(true);
        } else {
            saveButton.setVisible(true);
            unsaveButton.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_save_workout:
                saveWorkout();
                return true;
            case R.id.settings_unsave_workout:
                unsaveWorkout();
                return true;
            case R.id.settings_add_to_widget:
                addWidget();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
        return true;
    }

    public void saveWorkout() {

            Intent intent=getIntent();
            mWorkoutName=intent.getStringExtra(getString(R.string.workout_name_extra));
          //  Toast.makeText(this,mWorkoutName,Toast.LENGTH_LONG).show();
            mExerciseList=intent.getParcelableArrayListExtra(getString(R.string.exercise_list_extra));
            workoutdone=new Workout(mWorkoutName,mExerciseList);

            mFitViewModel.insert(workoutdone);

                unsaveButton.setVisible(true);
                saveButton.setVisible(false);


           // Toast.makeText(this, getString(R.string.toast_workout_saved),
                 //   Toast.LENGTH_SHORT).show();


    }

    public void unsaveWorkout() {
        Intent intent=getIntent();
        mWorkoutName=intent.getStringExtra(getString(R.string.workout_name_extra));
       // Toast.makeText(this,mWorkoutName,Toast.LENGTH_LONG).show();
        mExerciseList=intent.getParcelableArrayListExtra(getString(R.string.exercise_list_extra));
        workoutdone=new Workout(mWorkoutName,mExerciseList);

        mFitViewModel.delete(workoutdone);

            unsaveButton.setVisible(false);
            saveButton.setVisible(true);


       // Toast.makeText(this, getString(R.string.toast_workout_removed),
               // Toast.LENGTH_SHORT).show();
    }
    public void addWidget() {
        Intent widgetIntent = new Intent(this, WorkoutWidgetProvider.class);
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        widgetIntent.putExtra(getString(R.string.workout_name_extra), mWorkoutName);
        widgetIntent.putExtra(getString(R.string.exercise_list_extra), (Serializable) mExerciseList);
        sendBroadcast(widgetIntent);
        Toast.makeText(this, getString(R.string.toast_added_to_widget),
                Toast.LENGTH_SHORT).show();
    }
    public boolean isSaved()
    {

       workoutName =getIntent().getStringExtra(getString(R.string.workout_name_extra));
       saved=getIntent().getIntExtra("isSaved",0);
       mFitViewModel.getmAllWorkout().observe(this, new Observer<List<Workout>>() {
           @Override
           public void onChanged(List<Workout> workouts) {
               if ((workouts.size()>0 && !(workoutName.isEmpty()|| workoutName.equals("")))&&saved!=1)
               {
                   for (int i=0;i<workouts.size();i++)
                   {
                       if (workoutName.toString().toLowerCase().equals(workouts.get(i).getmName().toString().toLowerCase()))
                       {
                           saved=1;
                           break;
                       }
                       else
                           saved=0;
                      // Log.d("myTag",workouts.get(i).getmName().toString());
                   }
               }
               else if (saved==1)
               {
                   saved=1;
               }
               workoutName="";

           }
       });


     if (saved==1)
     {
         return true;
     }
     else
         return false;


    }




}
