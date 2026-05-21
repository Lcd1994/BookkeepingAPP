package com.moneytracker.app;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    private boolean isEditMode = false;
    private long editTransactionId = -1;
    
    private EditText amountEditText;
    private EditText noteEditText;
    private TextView dateTextView;
    private TextView titleTextView;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private Button saveButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        
        amountEditText = findViewById(R.id.amountEditText);
        noteEditText = findViewById(R.id.noteEditText);
        dateTextView = findViewById(R.id.dateTextView);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        TabLayout typeTabLayout = findViewById(R.id.typeTabLayout);
        saveButton = findViewById(R.id.saveButton);
        View backButton = findViewById(R.id.backButton);
        titleTextView = findViewById(R.id.titleTextView);
        
        Button btnAmount10 = findViewById(R.id.btnAmount10);
        Button btnAmount50 = findViewById(R.id.btnAmount50);
        Button btnAmount100 = findViewById(R.id.btnAmount100);
        Button btnAmount500 = findViewById(R.id.btnAmount500);
        
        btnAmount10.setOnClickListener(v -> {
            String current = amountEditText.getText().toString();
            if (current.isEmpty()) {
                amountEditText.setText("10");
            } else {
                amountEditText.setText(String.valueOf(Double.parseDouble(current) + 10));
            }
        });
        
        btnAmount50.setOnClickListener(v -> {
            String current = amountEditText.getText().toString();
            if (current.isEmpty()) {
                amountEditText.setText("50");
            } else {
                amountEditText.setText(String.valueOf(Double.parseDouble(current) + 50));
            }
        });
        
        btnAmount100.setOnClickListener(v -> {
            String current = amountEditText.getText().toString();
            if (current.isEmpty()) {
                amountEditText.setText("100");
            } else {
                amountEditText.setText(String.valueOf(Double.parseDouble(current) + 100));
            }
        });
        
        btnAmount500.setOnClickListener(v -> {
            String current = amountEditText.getText().toString();
            if (current.isEmpty()) {
                amountEditText.setText("500");
            } else {
                amountEditText.setText(String.valueOf(Double.parseDouble(current) + 500));
            }
        });
        
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
        
        Intent intent = getIntent();
        if (intent.hasExtra("transaction_id")) {
            isEditMode = true;
            editTransactionId = intent.getLongExtra("transaction_id", -1);
            double amount = intent.getDoubleExtra("amount", 0);
            int type = intent.getIntExtra("type", Transaction.TYPE_EXPENSE);
            String category = intent.getStringExtra("category");
            String note = intent.getStringExtra("note", "");
            long timestamp = intent.getLongExtra("timestamp", System.currentTimeMillis());
            
            amountEditText.setText(String.valueOf(amount));
            noteEditText.setText(note);
            selectedCategory = category;
            selectedTimestamp = timestamp;
            
            if (type == Transaction.TYPE_EXPENSE) {
                typeTabLayout.getTabAt(0).select();
            } else {
                typeTabLayout.getTabAt(1).select();
            }
            
            titleTextView.setText("编辑记录");
            saveButton.setText("更新");
            updateDateDisplay();
        }
        
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
            categories.add("通讯");
            categories.add("日用品");
            categories.add("美容");
            categories.add("运动");
            categories.add("零食");
            categories.add("饮品");
            categories.add("服装");
            categories.add("数码");
            categories.add("礼物");
            categories.add("旅游");
            categories.add("咖啡");
            categories.add("电影");
            categories.add("书籍");
            categories.add("保险");
            categories.add("其他");
        } else {
            categories.add("工资");
            categories.add("奖金");
            categories.add("投资");
            categories.add("理财");
            categories.add("兼职");
            categories.add("红包");
            categories.add("退款");
            categories.add("其他");
        }
        categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.setOnCategoryClickListener(category -> {
            selectedCategory = category;
        });
        
        if (selectedCategory != null) {
            categoryAdapter.setSelectedCategory(selectedCategory);
        }
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
        
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        
        if (isEditMode) {
            Transaction transaction = new Transaction(transactionType, amount, selectedCategory, note, selectedTimestamp);
            transaction.setId(editTransactionId);
            databaseHelper.updateTransaction(transaction);
            Toast.makeText(this, "记录已更新", Toast.LENGTH_SHORT).show();
        } else {
            Transaction transaction = new Transaction(transactionType, amount, selectedCategory, note, selectedTimestamp);
            databaseHelper.insertTransaction(transaction);
            Toast.makeText(this, "记录已保存", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
}
