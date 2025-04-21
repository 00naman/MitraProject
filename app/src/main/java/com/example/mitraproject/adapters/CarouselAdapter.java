package com.example.mitraproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitraproject.R;
import com.example.mitraproject.activities.ChatroomListActivity;
import com.example.mitraproject.ui.ChatActivity;
import com.example.mitraproject.ui.ChatFragment;
import com.example.mitraproject.ui.MoodFragment;
import com.example.mitraproject.ui.SandGameActivity;
import com.example.mitraproject.ui.SupportFragment;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CardViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String text);
    }

    private final List<String> items;
    private final OnItemClickListener listener;

    public CarouselAdapter(List<String> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String text = items.get(position);
        holder.textView.setText(text);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(text));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardText);
        }
    }
}
