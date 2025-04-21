package com.example.mitraproject.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mitraproject.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SupportFragment extends Fragment {

    private TextView tvStatus, tvName, tvDesc;
    private Button btnCall;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        tvStatus = view.findViewById(R.id.tv_location_status);
        tvName = view.findViewById(R.id.tv_helpline_name);
        tvDesc = view.findViewById(R.id.tv_helpline_desc);
        btnCall = view.findViewById(R.id.btn_call_helpline);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        fetchUserLocation();

        return view;
    }

    private void fetchUserLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                getPincodeFromLocation(location);
            } else {
                tvStatus.setText("Unable to detect location.");
            }
        });
    }

    private void getPincodeFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (!addresses.isEmpty()) {
                String pincode = addresses.get(0).getPostalCode();
                tvStatus.setText("Location detected. PIN: " + pincode);
                fetchHelplineFromFirestore(pincode);
            } else {
                tvStatus.setText("Unable to find pincode.");
            }
        } catch (IOException e) {
            tvStatus.setText("Error: " + e.getMessage());
        }
    }

    private void fetchHelplineFromFirestore(String pincode) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("helplines").document(pincode).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String name = documentSnapshot.getString("name");
                String phone = documentSnapshot.getString("phone");
                String desc = documentSnapshot.getString("description");

                tvName.setText(name);
                tvDesc.setText(desc);
                tvName.setVisibility(View.VISIBLE);
                tvDesc.setVisibility(View.VISIBLE);
                btnCall.setVisibility(View.VISIBLE);

                btnCall.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    startActivity(intent);
                });
            } else {
                tvStatus.setText("No helpline available for this area.");
            }
        }).addOnFailureListener(e -> tvStatus.setText("Failed to fetch data: " + e.getMessage()));
    }
}
