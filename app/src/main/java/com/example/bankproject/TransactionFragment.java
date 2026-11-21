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
import com.example.bankproject.adapters.TransactionAdapter;
import com.example.bankproject.models.Account;
import com.example.bankproject.models.Transaction;
import com.example.bankproject.models.TransferRequest;
import com.example.bankproject.network.RetrofitClient;
import com.example.bankproject.utils.SessionManager;

public class TransactionFragment extends Fragment {

    private EditText etSourceAccountId, etDestinationAccountId, etTransferAmount;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList = new ArrayList<>();
    private List<Long> myAccountIds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        etSourceAccountId = view.findViewById(R.id.etSourceAccountId);
        etDestinationAccountId = view.findViewById(R.id.etDestinationAccountId);
        etTransferAmount = view.findViewById(R.id.etTransferAmount);
        Button btnTransfer = view.findViewById(R.id.btnTransfer);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTransactions);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);

        loadTransactionsAndFilter();
        btnTransfer.setOnClickListener(v -> makeTransfer());

        return view;
    }

    private void loadTransactionsAndFilter() {
        SessionManager sessionManager = new SessionManager(getContext());
        Long customerId = sessionManager.getCustomerId();

        RetrofitClient.getAccountApi().getAccountsByCustomer(customerId).enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    myAccountIds.clear();
                    for (Account a : response.body()) {
                        myAccountIds.add(a.getId());
                    }
                    fetchAllAndFilterTransactions();
                }
            }
            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {}
        });
    }

    private void fetchAllAndFilterTransactions() {
        RetrofitClient.getTransactionApi().getTransactions().enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Transaction> filtered = new ArrayList<>();
                    for (Transaction t : response.body()) {
                        if (myAccountIds.contains(t.getSourceAccountId()) || myAccountIds.contains(t.getDestinationAccountId())) {
                            filtered.add(t);
                        }
                    }
                    transactionList = filtered;
                    adapter.setTransactions(transactionList);
                }
            }
            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {}
        });
    }

    private void makeTransfer() {
        String sourceStr = etSourceAccountId.getText().toString();
        String destStr = etDestinationAccountId.getText().toString();
        String amountStr = etTransferAmount.getText().toString();

        if (sourceStr.isEmpty() || destStr.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(getContext(), "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Long sourceId = Long.parseLong(sourceStr);
        if (!myAccountIds.contains(sourceId)) {
            Toast.makeText(getContext(), "Ce compte source ne vous appartient pas !", Toast.LENGTH_SHORT).show();
            return;
        }

        Long destId = Long.parseLong(destStr);
        BigDecimal amount = new BigDecimal(amountStr);

        TransferRequest request = new TransferRequest(sourceId, destId, amount);

        RetrofitClient.getTransactionApi().makeTransfer(request).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ("COMPLETED".equals(response.body().getStatus())) {
                        Toast.makeText(getContext(), "Virement réussi !", Toast.LENGTH_LONG).show();
                        etSourceAccountId.setText(""); etDestinationAccountId.setText(""); etTransferAmount.setText("");
                        loadTransactionsAndFilter();
                    } else {
                        Toast.makeText(getContext(), "Virement échoué (Solde insuffisant ou compte bloqué)", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Erreur lors du virement", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}