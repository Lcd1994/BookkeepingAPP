package com.moneytracker.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private int currentType = Transaction.TYPE_EXPENSE;
    private PieChart pieChart;
    private RecyclerView categoryStatsRecyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        
        databaseHelper = new DatabaseHelper(this);
        
        pieChart = findViewById(R.id.pieChart);
        categoryStatsRecyclerView = findViewById(R.id.categoryStatsRecyclerView);
        TabLayout statsTabLayout = findViewById(R.id.statsTabLayout);
        View backButton = findViewById(R.id.backButton);
        
        backButton.setOnClickListener(v -> finish());
        
        categoryStatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        statsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentType = tab.getPosition() == 0 ? Transaction.TYPE_EXPENSE : Transaction.TYPE_INCOME;
                loadStatistics();
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        
        setupPieChart();
        loadStatistics();
    }
    
    private void setupPieChart() {
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
    }
    
    private void loadStatistics() {
        List<DatabaseHelper.CategorySummary> summaries = databaseHelper.getCategorySummary(currentType);
        double totalAmount = currentType == Transaction.TYPE_EXPENSE 
                ? databaseHelper.getTotalExpense() 
                : databaseHelper.getTotalIncome();
        
        // 设置饼图
        List<PieEntry> entries = new ArrayList<>();
        for (DatabaseHelper.CategorySummary summary : summaries) {
            entries.add(new PieEntry((float) summary.getAmount(), summary.getCategory()));
        }
        
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);
        
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
        
        // 设置分类统计列表
        CategoryStatAdapter adapter = new CategoryStatAdapter(summaries, totalAmount);
        categoryStatsRecyclerView.setAdapter(adapter);
    }
}
