package com.moneytracker.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;
    private OnTransactionClickListener listener;

    public interface OnTransactionClickListener {
        void onTransactionClick(Transaction transaction);
        void onTransactionLongClick(Transaction transaction);
    }

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setOnTransactionClickListener(OnTransactionClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        
        holder.categoryText.setText(transaction.getCategory());
        holder.categoryIcon.setText(getCategoryIcon(transaction.getCategory()));
        
        if (transaction.getNote() != null && !transaction.getNote().isEmpty()) {
            holder.noteText.setText(transaction.getNote());
            holder.noteText.setVisibility(View.VISIBLE);
        } else {
            holder.noteText.setVisibility(View.GONE);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        holder.dateText.setText(sdf.format(new Date(transaction.getTimestamp())));
        
        String amountStr = String.format(Locale.getDefault(), "%.2f", transaction.getAmount());
        if (transaction.getType() == Transaction.TYPE_INCOME) {
            holder.amountText.setText("+" + amountStr);
            holder.amountText.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.income_color));
        } else {
            holder.amountText.setText("-" + amountStr);
            holder.amountText.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.expense_color));
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTransactionClick(transaction);
            }
        });
        
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onTransactionLongClick(transaction);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    private String getCategoryIcon(String category) {
        switch (category) {
            case "餐饮": return "🍽️";
            case "交通": return "🚗";
            case "购物": return "🛒";
            case "娱乐": return "🎮";
            case "教育": return "📚";
            case "医疗": return "🏥";
            case "住房": return "🏠";
            case "工资": return "💰";
            case "奖金": return "🎁";
            case "投资": return "📈";
            default: return "💳";
        }
    }

    public void updateData(List<Transaction> newTransactions) {
        this.transactions = newTransactions;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryIcon;
        TextView categoryText;
        TextView noteText;
        TextView dateText;
        TextView amountText;

        ViewHolder(View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categoryText = itemView.findViewById(R.id.categoryText);
            noteText = itemView.findViewById(R.id.noteText);
            dateText = itemView.findViewById(R.id.dateText);
            amountText = itemView.findViewById(R.id.amountText);
        }
    }
}
