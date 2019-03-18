package com.example.budgetapplication.WebApi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.budgetapplication.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.List;

public class BookAdapter extends  RecyclerView.Adapter<BookAdapter.CustomViewHolder>{
    private List<Book> bookList;
    private Context context;

    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";

    public BookAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        public final View mView;

        TextView txtTitle;
        TextView txtAuthor;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title_text);
            txtAuthor = mView.findViewById(R.id.author);
            coverImage = mView.findViewById(R.id.imageView);
        }

    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {


        holder.txtTitle.setText(bookList.get(position).getTitle());
        holder.txtAuthor.setText(bookList.get(position).getTitle());
        String imageURL = IMAGE_URL_BASE + bookList.get(position).getOpenId() + "-S.jpg";
        Picasso.with(context).load(imageURL).placeholder(R.drawable.ic_launcher_background).into(holder.coverImage);

    }
    @Override
    public int getItemCount() {
        return bookList.size();
    }

}

