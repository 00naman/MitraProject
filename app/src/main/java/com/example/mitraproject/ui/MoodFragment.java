package com.example.mitraproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mitraproject.R;

public class MoodFragment extends Fragment {

    private RadioGroup moodGroup, sleepGroup, stressGroup;
    private TextView tvInsights;

    public MoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood, container, false);

        moodGroup = view.findViewById(R.id.mood_group);
        sleepGroup = view.findViewById(R.id.sleep_group);
        stressGroup = view.findViewById(R.id.stress_group);
        tvInsights = view.findViewById(R.id.tv_insights);

        view.findViewById(R.id.btn_submit_mood).setOnClickListener(v -> showInsights());

        return view;
    }

    private void showInsights() {
        RadioButton moodSelected = getSelectedRadio(moodGroup);
        RadioButton sleepSelected = getSelectedRadio(sleepGroup);
        RadioButton stressSelected = getSelectedRadio(stressGroup);

        StringBuilder insights = new StringBuilder();

        if (moodSelected != null) {
            String mood = moodSelected.getText().toString();
            switch (mood) {
                case "😊":
                    insights.append("You're feeling good today. Keep up the positive vibes!\n");
                    break;
                case "😐":
                    insights.append("You're feeling neutral. Try something fun or relaxing today.\n");
                    break;
                case "😢":
                    insights.append("You're feeling low. It might help to talk to someone you trust.\n");
                    break;
            }
        }

        if (sleepSelected != null && sleepSelected.getText().toString().equals("No")) {
            insights.append("Lack of sleep can affect your mood. Try to get good rest tonight.\n");
        }

        if (stressSelected != null && stressSelected.getText().toString().equals("Yes")) {
            insights.append("You're feeling stressed. Consider meditation or talking it out.\n");
        }

        if (insights.length() == 0) {
            insights.append("Thanks for checking in. Keep taking care of yourself!");
        }

        tvInsights.setText(insights.toString().trim());
        tvInsights.setVisibility(View.VISIBLE);
    }

    private RadioButton getSelectedRadio(RadioGroup group) {
        int id = group.getCheckedRadioButtonId();
        return id != -1 ? group.findViewById(id) : null;
    }
}
