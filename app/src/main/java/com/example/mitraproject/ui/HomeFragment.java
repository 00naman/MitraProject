// src/main/java/com/example/mitraproject/ui/HomeFragment.java
package com.example.mitraproject.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.Navigation;
import com.example.mitraproject.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.btn_mood).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.moodFragment));

        view.findViewById(R.id.btn_chat).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.chatFragment));

        view.findViewById(R.id.btn_support).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.supportFragment));

        Button chatButton = view.findViewById(R.id.btn_open_chat);
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            startActivity(intent);
        });

        Button gameButton = view.findViewById(R.id.btn_anxiety_game);
        gameButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SandGameActivity.class);
            startActivity(intent);
        });


        return view;
    }
}
