package com.example.mitraproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mitraproject.R;
import com.example.mitraproject.activities.ChatroomListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPActivity extends AppCompatActivity {

    EditText etOtp;
    Button btnVerifyOtp;
    String verificationId;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        etOtp = findViewById(R.id.etOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        verificationId = getIntent().getStringExtra("verificationId");

        btnVerifyOtp.setOnClickListener(v -> {
            String code = etOtp.getText().toString().trim();
            if (code.isEmpty() || code.length() < 6) {
                etOtp.setError("Enter valid code");
                return;
            }

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(OTPActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OTPActivity.this, ChatroomListActivity.class));
                        finish();
                    } else {
                        Toast.makeText(OTPActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
