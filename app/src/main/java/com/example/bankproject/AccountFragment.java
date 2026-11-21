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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.bankproject.adapters.AccountAdapter;
import com.example.bankproject.models.Account;
import com.example.bankproject.network.RetrofitClient;
import com.example.bankproject.utils.SessionManager;

public class AccountFragment extends Fragment {

    private EditText etAccountNumber, etBalance;
    private AccountAdapter adapter;
    private List<Account> accountList = new ArrayList<>();
    private SessionManager sessionManager;
    private Long currentCustomerId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        sessionManager = new SessionManager(getContext());
        currentCustomerId = sessionManager.getCustomerId();

        etAccountNumber = view.findViewById(R.id.etAccountNumber);
        etBalance = view.findViewById(R.id.etBalance);
        Button btnAdd = view.findViewById(R.id.btnAddAccount);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAccounts);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AccountAdapter(accountList);
        recyclerView.setAdapter(adapter);

        loadAccounts();
        btnAdd.setOnClickListener(v -> addAccount());

        return view;
    }

    private void loadAccounts() {
        RetrofitClient.getAccountApi().getAccountsByCustomer(currentCustomerId).enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accountList = response.body();
                    adapter.setAccounts(accountList);
                }
            }
            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur réseau (GET)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAccount() {
        String accNum = etAccountNumber.getText().toString();
        String balanceStr = etBalance.getText().toString();

        if (accNum.isEmpty() || balanceStr.isEmpty()) {
            Toast.makeText(getContext(), "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Account a = new Account();
        a.setAccountNumber(accNum);
        a.setCustomerId(currentCustomerId);
        a.setBalance(new BigDecimal(balanceStr));

        RetrofitClient.getAccountApi().addAccount(a).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Demande envoyée ! En attente d'approbation.", Toast.LENGTH_LONG).show();
                    etAccountNumber.setText(""); etBalance.setText("");
                    loadAccounts();
                } else {
                    Toast.makeText(getContext(), "Erreur (Numéro existant ?)", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur réseau (POST)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}