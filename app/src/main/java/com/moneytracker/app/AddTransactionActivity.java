package com.moneytracker.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private int transactionType = Transaction.TYPE_EXPENSE;
    private String selectedCategory = null;
    private long selectedTimestamp = System.currentTimeMillis();
    
    private EditText amountEditText;
    private EditText noteEditText;
    private TextView dateTextView;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        
        amountEditText = findViewById(R.id.amountEditText);
        noteEditText = findViewById(R.id.noteEditText);
        dateTextView = findViewById(R.id.dateTextView);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        TabLayout typeTabLayout = findViewById(R.id.typeTabLayout);
        Button saveButton = findViewById(R.id.saveButton);
        View backButton = findViewById(R.id.backButton);
        
        backButton.setOnClickListener(v -> finish());
        
        updateDateDisplay();
        dateTextView.setOnClickListener(v -> showDatePicker());
        
        setupCategoryRecyclerView();
        
        typeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                transactionType = tab.getPosition() == 0 ? Transaction.TYPE_EXPENSE : Transaction.TYPE_INCOME;
                selectedCategory = null;
                updateCategories();
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        
        saveButton.setOnClickListener(v -> saveTransaction());
    }
    
    private void setupCategoryRecyclerView() {
        categoryAdapter = new CategoryAdapter(new ArrayList<>());
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(categoryAdapter);
        
        categoryAdapter.setOnCategoryClickListener(category -> {
            selectedCategory = category;
        });
        
        updateCategories();
    }
    
    private void updateCategories() {
        List<String> categories = new ArrayList<>();
        if (transactionType == Transaction.TYPE_EXPENSE) {
            categories.add("餐饮");
            categories.add("交通");
            categories.add("购物");
            categories.add("娱乐");
            categories.add("教育");
            categories.add("医疗");
            categories.add("住房");
            categories.add("其他");
        } else {
            categories.add("工资");
            categories.add("奖金");
            categories.add("投资");
            categories.add("其他");
        }
        categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.setOnCategoryClickListener(category -> {
            selectedCategory = category;
        });
    }
    
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedTimestamp);
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar newCalendar = Calendar.getInstance();
                    newCalendar.set(year, month, dayOfMonth);
                    selectedTimestamp = newCalendar.getTimeInMillis();
                    updateDateDisplay();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    
    private void updateDateDisplay() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateTextView.setText(sdf.format(new java.util.Date(selectedTimestamp)));
    }
    
    private void saveTransaction() {
        String amountStr = amountEditText.getText().toString().trim();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "金额格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (amount <= 0) {
            Toast.makeText(this, "金额必须大于0", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (selectedCategory == null) {
            Toast.makeText(this, "请选择分类", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String note = noteEditText.getText().toString().trim();
        
        Transaction transaction = new Transaction(transactionType, amount, selectedCategory, note, selectedTimestamp);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertTransaction(transaction);
        
        Toast.makeText(this, "记录已保存", Toast.LENGTH_SHORT).show();
        finish();
    }
}
