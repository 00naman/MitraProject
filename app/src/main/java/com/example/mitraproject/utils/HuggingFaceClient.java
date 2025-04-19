package com.example.mitraproject.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HuggingFaceClient {

    private static final String BASE_URL = "https://api-inference.huggingface.co/models/";
    private static HuggingFaceApi api;

    public static HuggingFaceApi getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(HuggingFaceApi.class);
        }
        return api;
    }
}
