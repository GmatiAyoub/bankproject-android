package com.example.bankproject.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitCustomer = null;
    private static Retrofit retrofitAccount = null;

    // Pour le Customer Service
    public static CustomerApi getApi() {
        if (retrofitCustomer == null) {
            retrofitCustomer = new Retrofit.Builder()
                    .baseUrl(CustomerApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitCustomer.create(CustomerApi.class);
    }

    // Pour le Account Service
    public static AccountApi getAccountApi() {
        if (retrofitAccount == null) {
            retrofitAccount = new Retrofit.Builder()
                    .baseUrl(AccountApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitAccount.create(AccountApi.class);
    }
}