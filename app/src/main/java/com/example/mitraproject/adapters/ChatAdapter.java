package com.example.mitraproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mitraproject.R;
import com.example.mitraproject.models.ChatMessage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.LayoutRes;
import android.widget.ArrayAdapter;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private final LayoutInflater inflater;

    public ChatAdapter(Context context, List<ChatMessage> messages) {
        super(context, 0, messages);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = getItem(position);
        return message != null ? message.getSender() : ChatMessage.SENDER_BOT; // fallback
    }

    @Override
    public int getViewTypeCount() {
        return 2; // User and Bot
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatMessage message = getItem(position);

        if (message == null) return new View(getContext());

        int viewType = getItemViewType(position);

        if (convertView == null) {
            int layout = (viewType == ChatMessage.SENDER_USER)
                    ? R.layout.item_message_user
                    : R.layout.item_message_bot;
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView messageText = convertView.findViewById(R.id.message_text);
        messageText.setText(message.getMessage());

        return convertView;
    }
}
