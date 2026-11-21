package com.example.bankproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.bankproject.R;
import com.example.bankproject.models.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) { this.transactions = transactions; }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction t = transactions.get(position);
        holder.tvTransactionDesc.setText("De " + t.getSourceAccountId() + " vers " + t.getDestinationAccountId() + " : " + t.getAmount() + " TND");

        // On colore le statut en vert si c'est COMPLETED, en rouge sinon
        if ("COMPLETED".equals(t.getStatus())) {
            holder.tvTransactionStatus.setText("Statut : " + t.getStatus());
            holder.tvTransactionStatus.setTextColor(0xFF4CAF50); // Vert
        } else {
            holder.tvTransactionStatus.setText("Statut : " + t.getStatus());
            holder.tvTransactionStatus.setTextColor(0xFFF44336); // Rouge
        }
    }

    @Override
    public int getItemCount() { return transactions != null ? transactions.size() : 0; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransactionDesc, tvTransactionStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransactionDesc = itemView.findViewById(R.id.tvTransactionDesc);
            tvTransactionStatus = itemView.findViewById(R.id.tvTransactionStatus);
        }
    }
}