package com.example.mitraproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.example.mitraproject.R;
import com.example.mitraproject.activities.ChatroomListActivity;
import com.example.mitraproject.activities.LoginActivity;

import java.util.Objects;


public class ChatFragment extends Fragment {

    Button button1;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        button1 = view.findViewById(R.id.loginbutton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;


    }
}
