package com.example.mitraproject.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.example.mitraproject.R;

public class SandGameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set custom falling sand view
        setContentView(new SandGameView(this));
    }
}
