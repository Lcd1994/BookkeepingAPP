package com.moneytracker.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<String> categories;
    private int selectedPosition = -1;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(String category);
    }

    public CategoryAdapter(List<String> categories) {
        this.categories = categories;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = categories.get(position);
        holder.categoryName.setText(category);
        holder.categoryIcon.setText(getCategoryIcon(category));
        holder.radioButton.setChecked(position == selectedPosition);
        
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
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
        RadioButton radioButton;

        ViewHolder(View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categoryName = itemView.findViewById(R.id.categoryName);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}
