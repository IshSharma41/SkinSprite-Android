package com.example.project_techmind_android;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Set timeout duration to a very large value or disable it
    private static final int TIMEOUT_SECONDS = 0; // Set timeout to zero for no timeout, or a large value in seconds

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                                          .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                                          .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                                          .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                                          .build();

            retrofit = new Retrofit.Builder()
                               .baseUrl(baseUrl)
                               .addConverterFactory(GsonConverterFactory.create())
                               .client(client)
                               .build();
        }
        return retrofit;
    }
}
