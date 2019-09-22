package com.udacity.befitness;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.befitness.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class WorkoutWidgetProvider extends AppWidgetProvider {
    private static String mWorkoutName = "";
    private static ArrayList<Exercise> mExerciseList = new ArrayList<Exercise>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetTitle = mWorkoutName;
        // Construct the RemoteViews object
        RemoteViews views =
                new RemoteViews(context.getPackageName(), R.layout.workout_widget_provider);
        if (!mWorkoutName.equals("") && !mExerciseList.isEmpty()) {
            views.setTextViewText(R.id.widget_workout_name, widgetTitle);
            views.setTextViewText(R.id.widget_exercise_text,
                    mExerciseList.get(0).getmDescription());
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String workoutNameExtra = context.getString(R.string.workout_name_extra);
        String exercisesListExtra = context.getString(R.string.exercise_list_extra);

        if (intent.hasExtra(workoutNameExtra) && intent.hasExtra(exercisesListExtra)) {
            mWorkoutName = intent.getStringExtra(workoutNameExtra);
            mExerciseList = intent.getParcelableArrayListExtra(exercisesListExtra);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context.getPackageName(),
                    WorkoutWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }
}
