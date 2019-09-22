package com.udacity.befitness.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.udacity.befitness.R;
import com.udacity.befitness.WorkoutDetailActivity;
import com.udacity.befitness.data.EquipmentAdapter;
import com.udacity.befitness.data.ExerciseAdapter;
import com.udacity.befitness.data.ExerciseCategoryAdapter;
import com.udacity.befitness.utils.NetworkUtils;


import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenerateFragment extends Fragment {

    private Context mContext;
    private int mStepPosition;

    @BindView(R.id.loading_layout) LinearLayout mLoadingLayout;
    @BindView(R.id.loading_animation) ImageView mLoadingImage;
    @BindView(R.id.generate_header) TextView mHeaderText;
    @BindView(R.id.muscle_group_rv) RecyclerView mCategoryView;
    @BindView(R.id.equipment_rv) RecyclerView mEquipmentView;
    @BindView(R.id.exercise_selector_rv) RecyclerView mExerciseView;
    @BindView(R.id.generate_fab) FloatingActionButton mGenerateFab;

    private RecyclerView.LayoutManager mCategoryLayoutManager;
    private RecyclerView.LayoutManager mEquipmentLayoutManager;
    private RecyclerView.LayoutManager mExerciseLayoutManager;

    private RecyclerView.Adapter mCategoryAdapter;
    private RecyclerView.Adapter mEquipmentAdapter;
    private RecyclerView.Adapter mExerciseAdapter;

    private String mCategoryJsonResults;
    private String mEquipmentJsonResults;
    private String mExerciseJsonResults;

    private AlertDialog.Builder mInputBuilder;
    private EditText mInputField;

    public GenerateFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_generate, container, false);
        ButterKnife.bind(this, view);
        mContext = container.getContext();

        mCategoryLayoutManager = new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false);
        mEquipmentLayoutManager = new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false);
        mExerciseLayoutManager = new LinearLayoutManager(getContext());

        mCategoryView.setLayoutManager(mCategoryLayoutManager);
        mEquipmentView.setLayoutManager(mEquipmentLayoutManager);
        mExerciseView.setLayoutManager(mExerciseLayoutManager);

        mGenerateFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                if (mStepPosition == 2) {
                    createInputDialog("");
                } else {
                    mStepPosition++;
                    swapStepView();
                }
            }
        });

        ((AppCompatActivity) mContext)
                .getSupportActionBar()
                .setTitle(getString(R.string.title_generate_full));
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mStepPosition = savedInstanceState.getInt(getString(R.string.generate_position_extra));
            mCategoryJsonResults =
                    savedInstanceState.getString(getString(R.string.category_json_extra));
            mEquipmentJsonResults =
                    savedInstanceState.getString(getString(R.string.equipment_json_extra));
            mExerciseJsonResults =
                    savedInstanceState.getString(getString(R.string.exercise_json_extra));
            String inputString = savedInstanceState
                    .getString(getString(R.string.input_string_extra));
            if (!TextUtils.isEmpty(mCategoryJsonResults)) {
                setCategoryAdapter(mCategoryJsonResults);
            }
            if (!TextUtils.isEmpty(mEquipmentJsonResults)) {
                setEquipmentAdapter(mEquipmentJsonResults);
            }
            if (!TextUtils.isEmpty(mExerciseJsonResults)) {
                setExerciseAdapter(mExerciseJsonResults);
            }
            if (inputString != null) {
                createInputDialog(inputString);
            }
            swapStepView();
        } else {
            resetFragment();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.generate_position_extra), mStepPosition);
        outState.putString(getString(R.string.category_json_extra), mCategoryJsonResults);
        outState.putString(getString(R.string.equipment_json_extra), mEquipmentJsonResults);
        outState.putString(getString(R.string.exercise_json_extra), mExerciseJsonResults);
        if (mInputField != null) {
            outState.putString(getString(R.string.input_string_extra),
                    mInputField.getText().toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void swapStepView() {
        switch (mStepPosition) {
            case 0:
                mHeaderText.setText(getString(R.string.muscle_header));
                mCategoryView.setVisibility(View.VISIBLE);
                mEquipmentView.setVisibility(View.GONE);
                mExerciseView.setVisibility(View.GONE);
                mGenerateFab.setImageResource(R.drawable.ic_next);
                break;
            case 1:
                mHeaderText.setText(getString(R.string.equipment_header));
                mCategoryView.setVisibility(View.GONE);
                mEquipmentView.setVisibility(View.VISIBLE);
                mExerciseView.setVisibility(View.GONE);
                mGenerateFab.setImageResource(R.drawable.ic_next);
                break;
            case 2:
                mHeaderText.setText(getString(R.string.exercises_header));
                showLoading();
                mCategoryView.setVisibility(View.GONE);
                mEquipmentView.setVisibility(View.GONE);
                mExerciseView.setVisibility(View.VISIBLE);
                mGenerateFab.setImageResource(R.drawable.ic_baseline_thumb_up_24px);
                if (mCategoryAdapter != null && mEquipmentAdapter != null
                        && TextUtils.isEmpty(mExerciseJsonResults)) {
                    new FetchExercisesTask().execute();
                }
                break;
            default:
                resetFragment();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void resetFragment() {
        mStepPosition = 0;
        ((AnimationDrawable) mLoadingImage.getBackground()).start();
        showLoading();
        mInputBuilder = null;
        mInputField = null;
        new FetchCategoriesTask().execute();
        new FetchEquipmentTask().execute();
        swapStepView();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void createInputDialog(String previousInput) {
        mInputBuilder = new AlertDialog.Builder(getActivity());
        mInputBuilder.setMessage(getString(R.string.enter_workout_name));
        mInputField = new EditText(getActivity());
        mInputField.setInputType(InputType.TYPE_CLASS_TEXT);
        mInputBuilder.setView(mInputField);
        if (!TextUtils.isEmpty(previousInput)) {
            mInputField.setText(previousInput);
        }
        mInputBuilder.setPositiveButton(getString(R.string.generate_button_text),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent detailIntent = new Intent(getActivity(), WorkoutDetailActivity.class);
                detailIntent.putExtra(getString(R.string.workout_name_extra),
                        mInputField.getText().toString());
                detailIntent.putParcelableArrayListExtra(getString(R.string.exercise_list_extra),
                        (ArrayList<? extends Parcelable>) ((ExerciseAdapter) mExerciseAdapter).getmSelectedExerciseList());
                getActivity().startActivity(detailIntent);
            }
        });
        mInputBuilder.setNegativeButton(getString(R.string.cancel_button_text),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mInputBuilder.show();
    }

    public void showLoading() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    public void setCategoryAdapter(String jsonString) {
        mCategoryAdapter = new ExerciseCategoryAdapter(getContext(), jsonString);
        mCategoryView.setAdapter(mCategoryAdapter);
    }

    public void setEquipmentAdapter(String jsonString) {
        mEquipmentAdapter = new EquipmentAdapter(getContext(), jsonString);
        mEquipmentView.setAdapter(mEquipmentAdapter);
    }

    public void setExerciseAdapter(String jsonString) {
        mExerciseAdapter = new ExerciseAdapter(getContext(), jsonString);
        mExerciseView.setAdapter(mExerciseAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class FetchCategoriesTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL categoriesRequestUrl = NetworkUtils.buildCategoriesUrl();
                return NetworkUtils.getResponseFromHttpUrl(categoriesRequestUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            hideLoading();
            mCategoryJsonResults = s;
            setCategoryAdapter(mCategoryJsonResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class FetchEquipmentTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL equipmentRequestUrl = NetworkUtils.buildEquipmentUrl();
                return NetworkUtils.getResponseFromHttpUrl(equipmentRequestUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            mEquipmentJsonResults = s;
            setEquipmentAdapter(mEquipmentJsonResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class FetchExercisesTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL exerciseRequestUrl = NetworkUtils.buildExerciseUrl
                        (((ExerciseCategoryAdapter) mCategoryAdapter).getmSelectedIdList(),
                                ((EquipmentAdapter) mEquipmentAdapter).getmSelectedIdList());
                return NetworkUtils.getResponseFromHttpUrl(exerciseRequestUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            hideLoading();
            mExerciseJsonResults = s;
            setExerciseAdapter(mExerciseJsonResults);
        }
    }
}