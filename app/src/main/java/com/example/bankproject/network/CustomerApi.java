package com.example.bankproject.network;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import com.example.bankproject.models.Customer;

public interface CustomerApi {
    String BASE_URL = "http://10.0.2.2:8081/";

    @GET("api/customers")
    Call<List<Customer>> getCustomers();

    @GET("api/customers/{id}")
    Call<Customer> getCustomerById(@Path("id") Long id);

    @POST("api/customers")
    Call<Customer> addCustomer(@Body Customer customer);

    @POST("api/customers/login")
    Call<Customer> login(@Body Map<String, String> credentials);
}