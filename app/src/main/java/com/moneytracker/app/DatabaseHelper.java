package com.moneytracker.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "moneytracker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TYPE + " INTEGER,"
                + COLUMN_AMOUNT + " REAL,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_NOTE + " TEXT,"
                + COLUMN_TIMESTAMP + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public long insertTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, transaction.getType());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_CATEGORY, transaction.getCategory());
        values.put(COLUMN_NOTE, transaction.getNote());
        values.put(COLUMN_TIMESTAMP, transaction.getTimestamp());

        long id = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
        return id;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS + " ORDER BY " + COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                transaction.setType(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                transaction.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)));
                transaction.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                transaction.setNote(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE)));
                transaction.setTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return transactions;
    }

    public double getTotalIncome() {
        double total = 0;
        String selectQuery = "SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_TRANSACTIONS 
                + " WHERE " + COLUMN_TYPE + " = " + Transaction.TYPE_INCOME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();
        db.close();
        return total;
    }

    public double getTotalExpense() {
        double total = 0;
        String selectQuery = "SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_TRANSACTIONS 
                + " WHERE " + COLUMN_TYPE + " = " + Transaction.TYPE_EXPENSE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();
        db.close();
        return total;
    }

    public List<CategorySummary> getCategorySummary(int type) {
        List<CategorySummary> summaries = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_CATEGORY + ", SUM(" + COLUMN_AMOUNT + ") as total"
                + " FROM " + TABLE_TRANSACTIONS 
                + " WHERE " + COLUMN_TYPE + " = " + type
                + " GROUP BY " + COLUMN_CATEGORY
                + " ORDER BY total DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CategorySummary summary = new CategorySummary();
                summary.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                summary.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("total")));
                summaries.add(summary);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return summaries;
    }

    public int deleteTransaction(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TRANSACTIONS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    public static class CategorySummary {
        private String category;
        private double amount;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}
