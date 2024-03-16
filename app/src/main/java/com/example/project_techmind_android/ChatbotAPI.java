package com.example.project_techmind_android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatbotAPI {
    @POST("/chat")
    Call<ChatbotResponse> sendMessage(@Body ChatbotRequest request);
}
