package com.example.bankproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.bankproject.R;
import com.example.bankproject.models.Account;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private List<Account> accounts;

    public AccountAdapter(List<Account> accounts) { this.accounts = accounts; }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account a = accounts.get(position);
        holder.tvAccountNumber.setText("Compte : " + a.getAccountNumber());
        holder.tvBalance.setText("Solde : " + a.getBalance() + " TND (Client ID: " + a.getCustomerId() + ")");
    }

    @Override
    public int getItemCount() { return accounts != null ? accounts.size() : 0; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccountNumber, tvBalance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvBalance = itemView.findViewById(R.id.tvBalance);
        }
    }
}