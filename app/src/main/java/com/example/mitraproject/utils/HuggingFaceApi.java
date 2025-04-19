package com.example.mitraproject.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HuggingFaceApi {

    @Headers({
            "Authorization: Bearer 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", // replace below
            "Content-Type: application/json"
    })
    @POST("microsoft/DialoGPT-medium")
    Call<List<HuggingFaceResponse>> getChatResponse(@Body HuggingFaceInput input);

}
