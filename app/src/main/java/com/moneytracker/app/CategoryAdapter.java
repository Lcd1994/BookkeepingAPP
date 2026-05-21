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
    
    public void setSelectedCategory(String category) {
        int position = categories.indexOf(category);
        if (position >= 0) {
            int previousPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        }
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
            case "通讯": return "📱";
            case "日用品": return "🧴";
            case "美容": return "💄";
            case "运动": return "⚽";
            case "零食": return "🍪";
            case "饮品": return "☕";
            case "服装": return "👔";
            case "数码": return "💻";
            case "礼物": return "🎁";
            case "旅游": return "✈️";
            case "咖啡": return "☕";
            case "电影": return "🎬";
            case "书籍": return "📖";
            case "保险": return "🛡️";
            case "工资": return "💰";
            case "奖金": return "🎉";
            case "投资": return "📈";
            case "理财": return "💹";
            case "兼职": return "💼";
            case "红包": return "🧧";
            case "退款": return "↩️";
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
