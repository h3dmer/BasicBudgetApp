package com.example.budgetapplication.WebApi;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("title")
    private String title;

    @SerializedName("author_name")
    private String[] author;

    @SerializedName("cover_i")
    private String openId;

    public Book(String title, String[] author, String openId) {
        this.title = title;
        this.author = author;
        this.openId = openId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author[0];
    }

    public String getOpenId() {
        return openId;
    }
}
