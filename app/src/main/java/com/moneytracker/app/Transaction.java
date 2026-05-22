package com.moneytracker.app;

public class Transaction {
    public static final int TYPE_EXPENSE = 0;
    public static final int TYPE_INCOME = 1;

    private long id;
    private int type;
    private double amount;
    private String category;
    private String note;
    private long timestamp;

    public Transaction() {
    }

    public Transaction(int type, double amount, String category, String note, long timestamp) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
