package com.moneytracker.app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AccountsActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        
        databaseHelper = new DatabaseHelper(this);
        
        TextView totalBalanceText = findViewById(R.id.totalBalanceText);
        View backButton = findViewById(R.id.backButton);
        
        backButton.setOnClickListener(v -> finish());
        
        double income = databaseHelper.getTotalIncome();
        double expense = databaseHelper.getTotalExpense();
        double balance = income - expense;
        
        totalBalanceText.setText(String.format(Locale.getDefault(), "¥%.2f", balance));
    }
}
