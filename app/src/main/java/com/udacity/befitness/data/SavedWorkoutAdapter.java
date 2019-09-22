package com.udacity.befitness.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.udacity.befitness.R;
import com.udacity.befitness.WorkoutDetailActivity;
import com.udacity.befitness.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class SavedWorkoutAdapter
        extends RecyclerView.Adapter<SavedWorkoutAdapter.SavedWorkoutAdapterViewHolder> {

    public Context mContext;
    public List<Workout> mWorkoutList;

    public class SavedWorkoutAdapterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mWorkoutCard;
        public TextView mNameText;

        public SavedWorkoutAdapterViewHolder(LinearLayout view) {
            super(view);
            mWorkoutCard = view;
            mNameText = new TextView(mContext);
            mWorkoutCard.addView(mNameText);
        }
    }

    public SavedWorkoutAdapter(Context context, ArrayList<Workout> workoutList) {
        mContext = context;
        mWorkoutList = workoutList;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public SavedWorkoutAdapterViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        LinearLayout workoutLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(15, 15, 15, 15);
        workoutLayout.setLayoutParams(cardParams);
        workoutLayout.setOrientation(LinearLayout.VERTICAL);
        workoutLayout.setBackgroundColor(mContext.getResources().getColor(R.color.card_back));
        workoutLayout.setPadding(10, 35, 10, 35);
        workoutLayout.setElevation(8);
        return new SavedWorkoutAdapter.SavedWorkoutAdapterViewHolder(workoutLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull SavedWorkoutAdapterViewHolder holder, final int position) {
        holder.mNameText.setText(mWorkoutList.get(position).getmName());
        holder.mNameText.setTextColor(mContext.getResources().getColor(R.color.card_text));
        holder.mNameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.mNameText.setTextSize(22);
        holder.mNameText.setTypeface(Typeface.DEFAULT_BOLD);
        holder.mNameText.setPadding(10, 10, 10, 10);

        holder.mWorkoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent workoutIntent = new Intent(mContext, WorkoutDetailActivity.class);

                workoutIntent.putExtra(mContext.getString(R.string.workout_name_extra),
                        mWorkoutList.get(position).getmName());
                workoutIntent.putParcelableArrayListExtra(mContext.getString
                                (R.string.exercise_list_extra),
                        (ArrayList<? extends Parcelable>) mWorkoutList.get(position).getmExerciseList());
                workoutIntent.putExtra("isSaved",1);
                mContext.startActivity(workoutIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }
}
