package com.example.mitraproject.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mitraproject.R;
import com.example.mitraproject.adapters.ChatAdapter;
import com.example.mitraproject.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText inputMessage;
    private ListView chatListView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputMessage = findViewById(R.id.input_message);
        ImageButton sendButton = findViewById(R.id.button_send);
        chatListView = findViewById(R.id.chat_list_view);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        chatListView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(v -> sendMessage());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendEmergencySMS();
        }
    }


    private void sendMessage() {
        String userInput = inputMessage.getText().toString().trim();
        if (!userInput.isEmpty()) {
            addMessageToChat(userInput, ChatMessage.SENDER_USER);
            inputMessage.setText("");

            String botReply = getRuleBasedResponse(userInput);
            addMessageToChat(botReply, ChatMessage.SENDER_BOT);
        }
    }

    // Rule-based response logic
    private String getRuleBasedResponse(String input) {
        input = input.toLowerCase();

        if (input.contains("die") || input.contains("kill") || input.contains("suicide")) {
            sendEmergencySMS();
            return "I'm really concerned about your safety. You're not alone — help is available. I've notified someone who can assist you.";
        }
        else if (input.contains("hi") || input.contains("hello")) {
            return "Hi there! How are you feeling today?";
        } else if (input.contains("sad") || input.contains("depressed") || input.contains("down")) {
            return "I'm really sorry you're feeling this way. You're not alone—talking helps. Would you like a breathing exercise or some tips to feel better?";
        } else if (input.contains("anxious") || input.contains("anxiety") || input.contains("panic")) {
            return "Anxiety can be tough. Try taking deep breaths. I'm here to guide you through a calming exercise if you’d like.";
        } else if (input.contains("happy") || input.contains("good")) {
            return "That's great to hear! 😊 Keep doing what makes you feel good.";
        } else if (input.contains("lonely") || input.contains("alone")) {
            return "Feeling lonely is tough. Remember, you're valued and there are people who care. Want to join a chatroom or hear something uplifting?";
        } else if (input.contains("help")) {
            return "I'm here for you. If you're in a crisis or need urgent help, please consider contacting a mental health professional or helpline.";
        } else {
            return "Tell me more about how you're feeling. I'm here to listen.";
        }
    }

    private void sendEmergencySMS() {
        String phoneNumber = "+918296354888"; // emergency contact number
        String emergencyMessage = "⚠️ URGENT: A user may be in crisis. Please check on them immediately.";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, emergencyMessage, null, null);
        }
    }


    private void addMessageToChat(String message, int sender) {
        ChatMessage chatMessage = new ChatMessage(message, sender);
        chatMessages.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
        chatListView.setSelection(chatMessages.size() - 1);
    }
}
