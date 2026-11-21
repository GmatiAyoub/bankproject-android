package com.example.bankproject.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitCustomer = null;
    private static Retrofit retrofitAccount = null;
    private static Retrofit retrofitTransaction = null; // Nouveau

    public static CustomerApi getApi() {
        if (retrofitCustomer == null) {
            retrofitCustomer = new Retrofit.Builder()
                    .baseUrl(CustomerApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitCustomer.create(CustomerApi.class);
    }

    public static AccountApi getAccountApi() {
        if (retrofitAccount == null) {
            retrofitAccount = new Retrofit.Builder()
                    .baseUrl(AccountApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitAccount.create(AccountApi.class);
    }

    // Pour le Transaction Service
    public static TransactionApi getTransactionApi() {
        if (retrofitTransaction == null) {
            retrofitTransaction = new Retrofit.Builder()
                    .baseUrl(TransactionApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitTransaction.create(TransactionApi.class);
    }
}