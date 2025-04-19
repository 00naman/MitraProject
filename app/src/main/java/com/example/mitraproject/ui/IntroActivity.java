package com.example.mitraproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mitraproject.MainActivity;
import com.example.mitraproject.R;

public class IntroActivity extends AppCompatActivity {

    private ImageView logoImage;
    private TextView appTitle;
    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        logoImage = findViewById(R.id.logoImage);
        appTitle = findViewById(R.id.appTitle);
        getStartedButton = findViewById(R.id.getStartedButton);

        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        // Set animations
        logoImage.startAnimation(fadeIn);
        appTitle.startAnimation(slideUp);
        getStartedButton.startAnimation(slideUp);

        getStartedButton.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Optionally auto-redirect without button after delay
        // new Handler().postDelayed(() -> {
        //     startActivity(new Intent(IntroActivity.this, MainActivity.class));
        //     finish();
        // }, 3000);
    }
}
