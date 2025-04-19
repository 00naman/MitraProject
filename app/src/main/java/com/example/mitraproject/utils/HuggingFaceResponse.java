package com.example.mitraproject.utils;

import com.google.gson.annotations.SerializedName;

public class HuggingFaceResponse {
    @SerializedName("generated_text")
    private String generatedText;

    public String getGeneratedText() {
        return generatedText;
    }

    public void setGeneratedText(String generatedText) {
        this.generatedText = generatedText;
    }
}

