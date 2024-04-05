package com.example.project_techmind_android;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/receive-image")
    Call<JsonObject> uploadImage(@Body RequestBody image);
}
