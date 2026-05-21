package com.moneytracker.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;
    
    private TextView balanceText;
    private TextView incomeText;
    private TextView expenseText;
    private RecyclerView transactionsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        databaseHelper = new DatabaseHelper(this);
        
        balanceText = findViewById(R.id.balanceText);
        incomeText = findViewById(R.id.incomeText);
        expenseText = findViewById(R.id.expenseText);
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        FloatingActionButton addFab = findViewById(R.id.addFab);
        
        transactions = new ArrayList<>();
        adapter = new TransactionAdapter(transactions);
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionsRecyclerView.setAdapter(adapter);
        
        adapter.setOnTransactionClickListener(new TransactionAdapter.OnTransactionClickListener() {
            @Override
            public void onTransactionClick(Transaction transaction) {
                Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                intent.putExtra("transaction_id", transaction.getId());
                intent.putExtra("amount", transaction.getAmount());
                intent.putExtra("type", transaction.getType());
                intent.putExtra("category", transaction.getCategory());
                intent.putExtra("note", transaction.getNote());
                intent.putExtra("timestamp", transaction.getTimestamp());
                startActivity(intent);
            }
            
            @Override
            public void onTransactionLongClick(Transaction transaction) {
                showDeleteDialog(transaction);
            }
        });
        
        addFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });
        
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_stats) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_accounts) {
                Intent intent = new Intent(MainActivity.this, AccountsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    
    private void loadData() {
        double income = databaseHelper.getTotalIncome();
        double expense = databaseHelper.getTotalExpense();
        double balance = income - expense;
        
        balanceText.setText(String.format(Locale.getDefault(), "¥%.2f", balance));
        incomeText.setText(String.format(Locale.getDefault(), "+¥%.2f", income));
        expenseText.setText(String.format(Locale.getDefault(), "-¥%.2f", expense));
        
        transactions = databaseHelper.getAllTransactions();
        adapter.updateData(transactions);
    }
    
    private void showDeleteDialog(Transaction transaction) {
        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("确定要删除这条记录吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    databaseHelper.deleteTransaction(transaction.getId());
                    loadData();
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
