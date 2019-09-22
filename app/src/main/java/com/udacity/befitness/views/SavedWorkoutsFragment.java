package com.udacity.befitness.views;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.udacity.befitness.R;

import com.udacity.befitness.data.SavedWorkoutAdapter;
import com.udacity.befitness.model.Workout;
import com.udacity.befitness.room.FitViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedWorkoutsFragment extends Fragment
         {
     private FitViewModel mFitViewModel;
     private List<Workout>workoutList;
    @BindView(R.id.saved_workouts_list_rv)
    RecyclerView mWorkoutListRecycler;
    @BindView(R.id.no_workouts_text_container) LinearLayout mNoWorkoutsContainer;
    private RecyclerView.Adapter mWorkoutAdapter;
    private RecyclerView.LayoutManager mWorkoutLayoutManager;

    private Context mContext;

    public SavedWorkoutsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_saved_workouts, container, false);
         if (mFitViewModel==null)
         {
             mFitViewModel = ViewModelProviders.of(this).get(FitViewModel.class);
         }


        workoutList=new ArrayList<>();

        ButterKnife.bind(this, view);
        mContext = container.getContext();

        mWorkoutLayoutManager = new LinearLayoutManager(mContext);
        mWorkoutListRecycler.setLayoutManager(mWorkoutLayoutManager);



        ((AppCompatActivity) mContext)
                .getSupportActionBar()
                .setTitle(getString(R.string.title_saved));
        setData();

        return view;
    }

private  void setData()
{
    mFitViewModel.getmAllWorkout().observe(this, new Observer<List<Workout>>() {
        @Override
        public void onChanged(List<Workout> workouts) {
            workoutList=new ArrayList<>();
            workoutList=workouts;
            mWorkoutAdapter = new SavedWorkoutAdapter(mContext, (ArrayList<Workout>) workoutList);
            mWorkoutListRecycler.setAdapter(mWorkoutAdapter);

            if (mWorkoutAdapter.getItemCount() == 0) {
                mNoWorkoutsContainer.setVisibility(View.VISIBLE);
            }
        }
    });
}
         }
