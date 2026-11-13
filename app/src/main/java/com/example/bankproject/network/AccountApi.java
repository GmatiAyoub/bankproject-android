package com.example.bankproject.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import com.example.bankproject.models.Account;

public interface AccountApi {
    // On utilise le port 8082 pour le Account Service
    String BASE_URL = "http://10.0.2.2:8082/";

    @GET("api/accounts")
    Call<List<Account>> getAccounts();

    @POST("api/accounts")
    Call<Account> addAccount(@Body Account account);
}