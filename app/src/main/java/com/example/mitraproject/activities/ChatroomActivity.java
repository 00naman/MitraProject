package com.example.mitraproject.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mitraproject.R;
import com.example.mitraproject.adapters.MessageAdapter;
import com.example.mitraproject.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ChatroomActivity extends AppCompatActivity {

    ListView messagesListView;
    FirebaseFirestore db;
    ArrayList<Message> messageList = new ArrayList<>();
    MessageAdapter messageAdapter;
    String chatroomId;
    String chatroomName;

    // Real-time listener reference
    ListenerRegistration messageListener;

    EditText etMessage;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        // Get chatroom info from intent
        chatroomId = getIntent().getStringExtra("chatroomId");
        chatroomName = getIntent().getStringExtra("chatroomName");

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Find views
        messagesListView = findViewById(R.id.messageListView);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        // Initialize message adapter
        messageAdapter = new MessageAdapter(this, messageList);
        messagesListView.setAdapter(messageAdapter);

        // Fetch messages initially
        fetchMessages();

        // Set up real-time listener for message updates
        setupRealTimeListener();

        // Set up the send button click listener
        btnSend.setOnClickListener(view -> {
            String messageText = etMessage.getText().toString().trim();

            // Ensure the message is not empty
            if (!TextUtils.isEmpty(messageText)) {
                sendMessage(messageText);
            } else {
                Toast.makeText(ChatroomActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMessages() {
        db.collection("chatrooms")
                .document(chatroomId)
                .collection("messages")
                .orderBy("timestamp") // Order messages by timestamp
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    messageList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String senderId = doc.getString("senderId");
                        String messageText = doc.getString("message");
                        long timestamp = doc.getLong("timestamp");

                        if (senderId != null && messageText != null) {
                            Message message = new Message(senderId, messageText, timestamp);
                            messageList.add(message);
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ChatroomActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void setupRealTimeListener() {
        // Listen to updates in real-time for messages in the current chatroom
        messageListener = db.collection("chatrooms")
                .document(chatroomId)
                .collection("messages")
                .orderBy("timestamp") // Order messages by timestamp
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(ChatroomActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        messageList.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String senderId = doc.getString("senderId");
                            String messageText = doc.getString("message");
                            long timestamp = doc.getLong("timestamp");

                            if (senderId != null && messageText != null) {
                                Message message = new Message(senderId, messageText, timestamp);
                                messageList.add(message);
                            }
                        }
                        messageAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void sendMessage(String messageText) {
        // Create a new message object
        String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        long timestamp = System.currentTimeMillis();

        Message message = new Message(senderId, messageText, timestamp);

        // Save the message to Firestore
        db.collection("chatrooms")
                .document(chatroomId)
                .collection("messages")
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    // Clear the input field after sending the message
                    etMessage.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ChatroomActivity.this, "Error sending message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the real-time listener when the activity is destroyed
        if (messageListener != null) {
            messageListener.remove();
        }
    }
}
