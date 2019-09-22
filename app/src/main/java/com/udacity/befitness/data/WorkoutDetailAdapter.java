package com.udacity.befitness.data;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.udacity.befitness.R;
import com.udacity.befitness.WorkoutDetailActivity;
import com.udacity.befitness.model.Exercise;
import com.udacity.befitness.views.ExerciseImageFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailAdapter
        extends RecyclerView.Adapter<WorkoutDetailAdapter.WorkoutViewHolder> {

    private Context mContext;
    private List<Exercise> mExerciseList;

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout exerciseCard;
        public TextView mNameText;
        public TextView mDescriptionText;
        public Button mImageButton;

        public WorkoutViewHolder(LinearLayout view) {
            super(view);
            exerciseCard = view;
            mNameText = new TextView(mContext);
            mDescriptionText = new TextView(mContext);
            mImageButton = new Button(mContext);
            exerciseCard.addView(mNameText);
            exerciseCard.addView(mDescriptionText);
            exerciseCard.addView(mImageButton);
        }
    }

    public WorkoutDetailAdapter(Context context, List<Exercise> exerciseList) {
        mContext = context;
        mExerciseList = exerciseList;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout exerciseLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(15, 15, 15, 15);
        exerciseLayout.setLayoutParams(cardParams);
        exerciseLayout.setOrientation(LinearLayout.VERTICAL);
        exerciseLayout.setBackgroundColor(mContext.getResources().getColor(R.color.card_back));
        exerciseLayout.setPadding(10, 15, 10, 15);
        exerciseLayout.setElevation(8);
        return new WorkoutDetailAdapter.WorkoutViewHolder(exerciseLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, final int position) {
        LinearLayout.LayoutParams contentParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.mNameText.setText(mExerciseList.get(position).getmName());
        holder.mNameText.setTextColor(mContext.getResources().getColor(R.color.card_text));
        holder.mNameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.mNameText.setTextSize(22);
        holder.mNameText.setTypeface(Typeface.DEFAULT_BOLD);
        holder.mNameText.setPadding(10, 10, 10, 10);

        holder.mDescriptionText.setText(mExerciseList.get(position).getmDescription());
        holder.mDescriptionText.setTextColor(mContext.getResources().getColor(R.color.card_text));
        holder.mDescriptionText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.mDescriptionText.setPadding(10, 5, 10, 5);

        holder.mImageButton.setVisibility(View.GONE);

        if (mExerciseList.get(position).getmImageUrlList().size() != 0) {
            holder.mImageButton.setVisibility(View.VISIBLE);
            holder.mImageButton.setText(mContext.getString(R.string.button_show_me_how));
            holder.mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExerciseImageFragment imageDetail = new ExerciseImageFragment();
                    Bundle exerciseInfo = new Bundle();
                    exerciseInfo.putStringArrayList(mContext.getString(R.string.image_list_extra),
                            (ArrayList<String>) mExerciseList.get(position).getmImageUrlList());
                    imageDetail.setArguments(exerciseInfo);
                    ((WorkoutDetailActivity) mContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.workout_detail_activity, imageDetail)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }
}
