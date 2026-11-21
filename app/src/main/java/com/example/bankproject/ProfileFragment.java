package com.example.bankproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.bankproject.models.Customer;
import com.example.bankproject.network.RetrofitClient;
import com.example.bankproject.utils.SessionManager;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvName = view.findViewById(R.id.tvProfileName);
        TextView tvEmail = view.findViewById(R.id.tvProfileEmail);
        TextView tvPhone = view.findViewById(R.id.tvProfilePhone);

        SessionManager sessionManager = new SessionManager(getContext());
        Long customerId = sessionManager.getCustomerId();

        RetrofitClient.getApi().getCustomerById(customerId).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Customer c = response.body();
                    tvName.setText(c.getFirstName() + " " + c.getLastName());
                    tvEmail.setText("📧 " + c.getEmail());
                    tvPhone.setText("📱 " + c.getPhone());
                }
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}