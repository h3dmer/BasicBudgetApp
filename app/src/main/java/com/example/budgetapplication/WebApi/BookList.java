package com.example.budgetapplication.WebApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {

    @SerializedName("docs")
    List<Book> bookList;

    public List<Book> getBookList() {
        return bookList;
    }
}