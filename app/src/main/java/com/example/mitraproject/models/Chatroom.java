package com.example.mitraproject.models;

public class Chatroom {
    private String id;
    private String name;

    public Chatroom() {
        // Required for Firebase
    }

    public Chatroom(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
