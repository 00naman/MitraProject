package com.example.mitraproject.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.example.mitraproject.R;

public class SupportFragment extends Fragment {

    public SupportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        // KIRAN Helpline
        Button btnKiran = view.findViewById(R.id.btn_call_kiran);
        btnKiran.setOnClickListener(v -> callNumber("18005990019"));

        // Vandrevala Foundation
        Button btnVandrevala = view.findViewById(R.id.btn_call_vandrevala);
        btnVandrevala.setOnClickListener(v -> callNumber("18602662345")); // or "9999666555"

        // Snehi
        Button btnSnehi = view.findViewById(R.id.btn_call_snehi);
        btnSnehi.setOnClickListener(v -> callNumber("9582208181"));

        // iCall TISS
        Button btnICall = view.findViewById(R.id.btn_call_icall);
        btnICall.setOnClickListener(v -> callNumber("9152987821"));

        return view;
    }

    private void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }
}
