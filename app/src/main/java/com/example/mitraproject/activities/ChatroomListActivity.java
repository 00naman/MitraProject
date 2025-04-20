package com.example.mitraproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mitraproject.R;
import com.example.mitraproject.models.Chatroom;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ChatroomListActivity extends AppCompatActivity {

    ListView chatroomListView;
    FirebaseFirestore db;
    ArrayList<Chatroom> chatroomList = new ArrayList<>();
    ArrayList<String> chatroomNames = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        chatroomListView = findViewById(R.id.chatroomListView);
        db = FirebaseFirestore.getInstance();

        // Create an adapter to populate the ListView with chatroom names
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatroomNames);
        chatroomListView.setAdapter(adapter);

        // Fetch chatrooms from Firestore
        fetchChatrooms();

        // Handle item click to start ChatroomActivity
        chatroomListView.setOnItemClickListener((parent, view, position, id) -> {
            Chatroom selected = chatroomList.get(position);
            Intent intent = new Intent(ChatroomListActivity.this, ChatroomActivity.class);
            intent.putExtra("chatroomId", selected.getId());
            intent.putExtra("chatroomName", selected.getName());
            startActivity(intent);
        });
    }

    // Method to fetch chatrooms from Firestore
    private void fetchChatrooms() {
        db.collection("chatrooms")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    chatroomList.clear();
                    chatroomNames.clear();

                    // Log the number of chatrooms fetched
                    Log.d("ChatroomListActivity", "Fetched " + queryDocumentSnapshots.size() + " chatrooms.");

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Chatroom chatroom = new Chatroom(doc.getId(), doc.getString("name"));
                        chatroomList.add(chatroom);
                        chatroomNames.add(chatroom.getName());

                        // Log the name of each chatroom
                        Log.d("ChatroomListActivity", "Chatroom: " + chatroom.getName());
                    }
                    // Update the ListView with the new data
                    adapter.notifyDataSetChanged();

                    // Log size of chatroomNames to check if it's populated
                    Log.d("ChatroomListActivity", "Chatroom names size: " + chatroomNames.size());
                })
                .addOnFailureListener(e -> {
                    // Log error if fetching fails
                    Log.e("ChatroomListActivity", "Error fetching chatrooms: " + e.getMessage());
                    Toast.makeText(ChatroomListActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Test with static data to check if ListView is working
        // Uncomment the below code if needed to test the ListView with static data
        // chatroomNames.add("Chatroom 1");
        // chatroomNames.add("Chatroom 2");
        // chatroomNames.add("Chatroom 3");
        // adapter.notifyDataSetChanged();
    }
}
