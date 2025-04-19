package com.example.mitraproject.utils;

import android.content.Context;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class TFLiteChatbot {

    private Interpreter interpreter;

    public TFLiteChatbot(Context context) {
        try {
            interpreter = new Interpreter(loadModelFile(context, "chatbot.tflite"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MappedByteBuffer loadModelFile(Context context, String filename) throws IOException {
        FileInputStream fileInputStream = context.getAssets().openFd(filename).createInputStream();
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = context.getAssets().openFd(filename).getStartOffset();
        long declaredLength = context.getAssets().openFd(filename).getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public String getResponse(String input) {
        // Placeholder implementation – replace with real model prediction logic
        if (input.toLowerCase().contains("anxious") || input.toLowerCase().contains("nervous")) {
            return "It sounds like you're feeling anxious. Let's try a calming technique.";
        } else if (input.toLowerCase().contains("sad") || input.toLowerCase().contains("depressed")) {
            return "I'm here for you. You are not alone. Want to talk more about it?";
        } else if (input.toLowerCase().contains("happy") || input.toLowerCase().contains("excited")) {
            return "That's great to hear! What’s making you feel good today?";
        } else {
            return "Thanks for sharing. How are you feeling now?";
        }
    }
}
