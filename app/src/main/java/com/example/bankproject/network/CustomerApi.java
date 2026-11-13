package com.example.bankproject.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import com.example.bankproject.models.Customer;

public interface CustomerApi {
    // 10.0.2.2 est l'adresse de ton PC (Docker) vue depuis l'émulateur Android
    String BASE_URL = "http://10.0.2.2:8081/";

    @GET("api/customers")
    Call<List<Customer>> getCustomers();

    @POST("api/customers")
    Call<Customer> addCustomer(@Body Customer customer);
}