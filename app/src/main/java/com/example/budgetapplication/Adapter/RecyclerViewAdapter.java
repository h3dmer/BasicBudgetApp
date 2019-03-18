package com.example.budgetapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetapplication.Model.Expense;
import com.example.budgetapplication.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Expense> mExpense;

    public RecyclerViewAdapter(Context mContext, List<Expense> mExpense) {
        this.mContext = mContext;
        this.mExpense = mExpense;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.expense_display_layout, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.date.setText(mExpense.get(i).getDate());
        myViewHolder.price.setText(mExpense.get(i).getPrice());
        myViewHolder.category.setText(mExpense.get(i).getCategory());

    }

    @Override
    public int getItemCount() {
        return mExpense.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date, price, category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.text_view_date);
            price = itemView.findViewById(R.id.text_view_price);
            category = itemView.findViewById(R.id.text_view_category);


        }


    }
}
