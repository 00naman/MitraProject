package com.example.mitraproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mitraproject.R;
import com.example.mitraproject.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageAdapter extends ArrayAdapter<Message> {

    private Context context;
    private ArrayList<Message> messages;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
        this.context = context;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        }

        Message message = getItem(position);

        TextView senderTextView = convertView.findViewById(R.id.tvSender);
        TextView contentTextView = convertView.findViewById(R.id.tvMessage);

        senderTextView.setText(message.getSenderId());  // Display sender's name or ID
        contentTextView.setText(message.getMessage());  // Display message content

        return convertView;
    }
}
