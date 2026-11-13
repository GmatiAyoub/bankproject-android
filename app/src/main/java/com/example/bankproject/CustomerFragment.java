package com.example.bankproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.bankproject.adapters.CustomerAdapter;
import com.example.bankproject.models.Customer;
import com.example.bankproject.network.RetrofitClient;

public class CustomerFragment extends Fragment {

    private EditText etFirstName, etLastName, etEmail, etPhone;
    private CustomerAdapter adapter;
    private List<Customer> customerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CustomerAdapter(customerList);
        recyclerView.setAdapter(adapter);

        loadCustomers(); // Charger les clients au démarrage
        btnAdd.setOnClickListener(v -> addCustomer());

        return view;
    }

    private void loadCustomers() {
        RetrofitClient.getApi().getCustomers().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    customerList = response.body();
                    adapter.setCustomers(customerList);
                }
            }
            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur réseau (GET)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCustomer() {
        String fn = etFirstName.getText().toString();
        String ln = etLastName.getText().toString();
        String em = etEmail.getText().toString();
        String ph = etPhone.getText().toString();

        if (fn.isEmpty() || ln.isEmpty() || em.isEmpty()) {
            Toast.makeText(getContext(), "Remplissez les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Customer c = new Customer();
        c.setFirstName(fn);
        c.setLastName(ln);
        c.setEmail(em);
        c.setPhone(ph);

        RetrofitClient.getApi().addCustomer(c).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Client ajouté !", Toast.LENGTH_SHORT).show();
                    etFirstName.setText(""); etLastName.setText(""); etEmail.setText(""); etPhone.setText("");
                    loadCustomers(); // Recharger la liste
                } else {
                    Toast.makeText(getContext(), "Erreur (Email existant ?)", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur réseau (POST)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}