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
import androidx.viewpager2.widget.ViewPager2;

import com.example.mitraproject.R;
import com.example.mitraproject.adapters.CarouselAdapter;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager2 viewPager = view.findViewById(R.id.cardCarousel);

        List<String> cardItems = Arrays.asList(
                "How are you today!",
                "Talk to other people",
                "Get Support",
                "Relief",
                "Mitra Chat"
        );

        CarouselAdapter adapter = new CarouselAdapter(cardItems, text -> {
            switch (text) {
                case "How are you today!":
                    Navigation.findNavController(view).navigate(R.id.moodFragment);
                    break;
                case "Talk to other people":
                    Navigation.findNavController(view).navigate(R.id.chatFragment);
                    break;
                case "Get Support":
                    Navigation.findNavController(view).navigate(R.id.supportFragment);
                    break;
                case "Relief":
                    startActivity(new Intent(getActivity(), SandGameActivity.class));
                    break;
                case "Mitra Chat":
                    startActivity(new Intent(getActivity(), ChatActivity.class));
                    break;
            }
        });

        viewPager.setAdapter(adapter);
        return view;
    }
}
