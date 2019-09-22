package com.udacity.befitness.data;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;


import com.udacity.befitness.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {

    public static final String JSON_RESULTS = "results";
    public static final String JSON_NAME = "name";
    public static final String JSON_ID = "id";

    public Context mContext;
    private List<Pair<Integer, String>> mEquipmentList;
    private List<Integer> mSelectedIdList;

    public class EquipmentViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mTextHolder;
        public TextView mEquipmentText;
        public int mEquipmentId;
        public boolean selected;

        public EquipmentViewHolder(LinearLayout view) {
            super(view);
            mTextHolder = view;
            mEquipmentText = new TextView(mContext);
            mTextHolder.addView(mEquipmentText);
            selected = false;
        }

        public void toggleViewSelected() {
            if (selected) {
                mTextHolder.setBackgroundResource(R.color.card_back);
                selected = false;
                for (int i = 0; i < mSelectedIdList.size(); i++) {
                    if (mSelectedIdList.get(i) == mEquipmentId) {
                        mSelectedIdList.remove(i);
                        break;
                    }
                }
            } else {
                mTextHolder.setBackgroundResource(R.color.colorPrimaryDark);
                selected = true;
                mSelectedIdList.add(mEquipmentId);
            }
        }
    }

    public EquipmentAdapter(Context context, String jsonString) {
        mContext = context;
        mEquipmentList = new ArrayList<>();
        mSelectedIdList = new ArrayList<>();
        extractEquipment(jsonString);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout equipmentLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams holderParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        holderParams.setMargins(15, 15, 15, 15);
        equipmentLayout.setLayoutParams(holderParams);
        equipmentLayout.setPadding(50, 200, 50, 200);
        equipmentLayout.setBackgroundResource(R.color.card_back);
        equipmentLayout.setElevation(8);
        EquipmentAdapter.EquipmentViewHolder equipmentViewHolder =
                new EquipmentAdapter.EquipmentViewHolder(equipmentLayout);
        return equipmentViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
        Pair<Integer, String> currentPair = mEquipmentList.get(position);
        holder.mEquipmentId = currentPair.first;
        LinearLayout.LayoutParams textParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        textParams.gravity = Gravity.CENTER;
        holder.mEquipmentText.setLayoutParams(textParams);
        holder.mEquipmentText.setText(currentPair.second);
        holder.mEquipmentText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.mEquipmentText.setTextColor(mContext.getResources().getColor(R.color.card_text));
        holder.mEquipmentText.setTextSize(20);

        final EquipmentAdapter.EquipmentViewHolder holderIn = holder;
        holder.mTextHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holderIn.toggleViewSelected();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEquipmentList.size();
    }

    public void extractEquipment(String jsonString) {
        try {
            JSONArray equipmentJson = new JSONObject(jsonString).getJSONArray(JSON_RESULTS);
            for (int i = 0; i < equipmentJson.length(); i++) {
                String equipmentName = equipmentJson.getJSONObject(i)
                        .getString(JSON_NAME);
                int equipmentId = equipmentJson.getJSONObject(i)
                        .getInt(JSON_ID);
                Pair<Integer, String > equipmentPair = new Pair<>(equipmentId, equipmentName);
                mEquipmentList.add(equipmentPair);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Pair<Integer, String>> getmEquipmentList() {
        return mEquipmentList;
    }

    public void setmEquipmentList(ArrayList<Pair<Integer, String>> mEquipmentList) {
        this.mEquipmentList = mEquipmentList;
    }

    public List<Integer> getmSelectedIdList() {
        return mSelectedIdList;
    }

    public void setmSelectedIdList(ArrayList<Integer> mSelectedIdList) {
        this.mSelectedIdList = mSelectedIdList;
    }
}
