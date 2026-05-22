package com.moneytracker.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class CategoryStatAdapter extends RecyclerView.Adapter<CategoryStatAdapter.ViewHolder> {
    private List<DatabaseHelper.CategorySummary> summaries;
    private double totalAmount;

    public CategoryStatAdapter(List<DatabaseHelper.CategorySummary> summaries, double totalAmount) {
        this.summaries = summaries;
        this.totalAmount = totalAmount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatabaseHelper.CategorySummary summary = summaries.get(position);
        holder.categoryName.setText(summary.getCategory());
        holder.categoryIcon.setText(getCategoryIcon(summary.getCategory()));
        holder.amountText.setText(String.format(Locale.getDefault(), "%.2f", summary.getAmount()));
        
        int progress = totalAmount > 0 ? (int) ((summary.getAmount() / totalAmount) * 100) : 0;
        holder.progressBar.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return summaries.size();
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryIcon;
        TextView categoryName;
        TextView amountText;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categoryName = itemView.findViewById(R.id.categoryName);
            amountText = itemView.findViewById(R.id.amountText);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
