package com.example.mitraproject.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mitraproject.R;
import com.example.mitraproject.adapters.ChatAdapter;
import com.example.mitraproject.models.ChatMessage;
import com.example.mitraproject.utils.HuggingFaceClient;
import com.example.mitraproject.utils.HuggingFaceInput;
import com.example.mitraproject.utils.HuggingFaceResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private EditText inputMessage;
    private ImageButton sendButton;
    private ListView chatListView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize UI elements
        inputMessage = findViewById(R.id.input_message);
        sendButton = findViewById(R.id.button_send);
        chatListView = findViewById(R.id.chat_list_view);

        // Initialize chat list and adapter
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        chatListView.setAdapter(chatAdapter);

        // Send button click listener
        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String userInput = inputMessage.getText().toString().trim();
        if (!userInput.isEmpty()) {
            // Add user message
            addMessageToChat(userInput, ChatMessage.SENDER_USER);
            inputMessage.setText("");

            // Fetch bot response
            getBotResponse(userInput);
        }
    }

    private void getBotResponse(String userMessage) {
        HuggingFaceInput input = new HuggingFaceInput(userMessage);

        HuggingFaceClient.getApi().getChatResponse(input).enqueue(new Callback<List<HuggingFaceResponse>>() {
            @Override
            public void onResponse(Call<List<HuggingFaceResponse>> call, Response<List<HuggingFaceResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String botReply = response.body().get(0).getGeneratedText();
                    addMessageToChat(botReply, ChatMessage.SENDER_BOT);
                } else {
                    addMessageToChat("Sorry, I didn't understand that.", ChatMessage.SENDER_BOT);
                }
            }

            @Override
            public void onFailure(Call<List<HuggingFaceResponse>> call, Throwable t) {
                addMessageToChat("Error: " + t.getMessage(), ChatMessage.SENDER_BOT);
            }
        });
    }

    private void addMessageToChat(String message, int sender) {
        ChatMessage chatMessage = new ChatMessage(message, sender);
        chatMessages.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
        chatListView.setSelection(chatMessages.size() - 1);
    }
}
