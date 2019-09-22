package com.udacity.befitness.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.udacity.befitness.R;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseImageFragment extends Fragment {

    private ArrayList<String> mExerciseImageList;
    @BindView(R.id.exercise_image_holder) LinearLayout mExerciseImageHolder;

    public ExerciseImageFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_exercise_image, container, false);
        ButterKnife.bind(this, view);
        mExerciseImageList =
                getArguments().getStringArrayList(getString(R.string.image_list_extra));
        mExerciseImageHolder.removeAllViews();
        for (String s : mExerciseImageList) {
            ImageView exerciseImage = new ImageView(getContext());
            Picasso.get().load(s).into(exerciseImage);
            exerciseImage.setContentDescription(s);
            mExerciseImageHolder.addView(exerciseImage);
        }
        return view;
    }
}
