package com.example.bankproject.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import com.example.bankproject.models.Account;

public interface AccountApi {
    String BASE_URL = "http://10.0.2.2:8082/";

    @GET("api/accounts")
    Call<List<Account>> getAccounts();

    @GET("api/accounts/customer/{customerId}")
    Call<List<Account>> getAccountsByCustomer(@Path("customerId") Long customerId);

    @POST("api/accounts")
    Call<Account> addAccount(@Body Account account);
}