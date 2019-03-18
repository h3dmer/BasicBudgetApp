package com.example.budgetapplication.WebApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookClient {

    @GET("search.json")
    Call<BookList> getBooks(@Query("q") String query);
}
