package com.example.bankproject.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import com.example.bankproject.models.Transaction;
import com.example.bankproject.models.TransferRequest;

public interface TransactionApi {
    // On utilise le port 8083 pour le Transaction Service
    String BASE_URL = "http://10.0.2.2:8083/";

    @GET("api/transactions")
    Call<List<Transaction>> getTransactions();

    @POST("api/transactions/transfer")
    Call<Transaction> makeTransfer(@Body TransferRequest request);
}