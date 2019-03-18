package com.example.budgetapplication.Model;

public class Expense {

    private String expenseId;
    private String price;
    private String category;
    private String date;

    public Expense() {

    }

    public Expense(String expenseId, String price, String category, String date) {
        this.expenseId = expenseId;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public Expense(String price, String category, String date) {
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
