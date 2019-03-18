package com.example.budgetapplication.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.budgetapplication.MainActivity;
import com.example.budgetapplication.R;
import com.example.budgetapplication.WebApi.Book;
import com.example.budgetapplication.WebApi.BookAdapter;
import com.example.budgetapplication.WebApi.BookClient;
import com.example.budgetapplication.WebApi.BookList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryFragment extends Fragment {

    private ListView listView;
    private Button button;
    private EditText editText;
    private BookAdapter adapter;
    private RecyclerView recyclerView;
    private View libView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        libView = inflater.inflate(R.layout.fragment_library, container, false);

        //editText = libView.findViewById(R.id.search);
        //button = libView.findViewById(R.id.button);

        /*button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queryBooks(editText.getText().toString());
            }
        });*/

        String temp = getString(R.string.englishLib);
        String eng = getString(R.string.englishLib);

        if(temp.equals(eng)) {
            String kuery = "how to save money";
            queryBooks(kuery);
        }else {
            String ku = "oszczedzanie pieniedzy";
            queryBooks(ku);
        }

        return libView;
    }

    private void queryBooks(String searchString) {
        String urlString = "";

        try {
            urlString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            // if this fails for some reason, let the user know why
            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://openlibrary.org/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        BookClient client=  retrofit.create(BookClient.class);

        Call<BookList> call = client.getBooks(urlString);

        call.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                Log.i("mojlog","sukces");


                BookList bookList = response.body();
                List<Book> books= bookList.getBookList();

                generateDataList(books);

            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                Log.i("mojlog","nie sukcess");

            }
        });
    }
    private void generateDataList(List<Book> bookList) {
        recyclerView = libView.findViewById(R.id.recycle_view);
        adapter = new BookAdapter(bookList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }





}
