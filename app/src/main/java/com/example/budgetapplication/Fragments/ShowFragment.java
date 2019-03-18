package com.example.budgetapplication.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetapplication.Model.Expense;
import com.example.budgetapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.mikephil.charting.charts.Chart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ShowFragment extends Fragment {

    private View ExpensesFragmentView;
    private RecyclerView myExpensesList;

    private DatabaseReference ExpenseRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    private ImageButton btnGoCalendar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;



    public ShowFragment() {

    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExpensesFragmentView = inflater.inflate(R.layout.fragment_show, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        ExpenseRef = FirebaseDatabase.getInstance().getReference().child("expenses").child(currentUserID);



        myExpensesList = (RecyclerView) ExpensesFragmentView.findViewById(R.id.expenseRecyclerView);
        myExpensesList.setLayoutManager(new LinearLayoutManager(getContext()));



        return ExpensesFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Expense> options =
                new FirebaseRecyclerOptions.Builder<Expense>()
                .setQuery(ExpenseRef, Expense.class)
                .build();


        FirebaseRecyclerAdapter<Expense, RequestViewHolder> adapter =
                new FirebaseRecyclerAdapter<Expense, RequestViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final RequestViewHolder holder, final int position, @NonNull Expense model) {



                        holder.userDate.setText(model.getDate());
                        holder.userPrice.setText(model.getPrice());
                        holder.userCategory.setText(model.getCategory());

                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                final String currentDate = getItem(position).getDate();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Delete");
                                builder.setMessage("Are you sure to delete?");

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Query mQuery = ExpenseRef.orderByChild("date").equalTo(currentDate);

                                        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot ds: dataSnapshot.getChildren()){
                                                    ds.getRef().removeValue();

                                                    Toast.makeText(getContext(), "Record delete", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(getContext(), "Something gone wrong ಠ.ಠ", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "◙‿◙", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                builder.create().show();


                                return true;
                            }
                        });


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String currentDate = getItem(position).getDate();
                                final String currentExpId = getItem(position).getExpenseId();
                                final String currentPrice = getItem(position).getPrice();
                                final String currentCategory = getItem(position).getCategory();

                                showUpdateDialog(currentExpId, currentDate, currentPrice, currentCategory);

                            }
                        });

                    }


                    @NonNull
                    @Override
                    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expense_display_layout, viewGroup, false);
                        RequestViewHolder holder = new RequestViewHolder(view);
                        return holder;

                    }

                };
        myExpensesList.setAdapter(adapter);
        adapter.startListening();
    }

    private void showUpdateDialog(final String expId, final String date, String price, final String category) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.updatePrice);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
        final Spinner spinner = dialogView.findViewById(R.id.updateSpinner);
        final Button btnGoCalendar = dialogView.findViewById(R.id.calButton);
        final TextView theDate = dialogView.findViewById(R.id.dialogDate);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d("AddFragment", "onDateSet: date : " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth+"/"+month+"/"+year;

                theDate.setText(date);
            }
        };

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = editText.getText().toString().trim();
                String cat = spinner.getSelectedItem().toString();
                String date = theDate.getText().toString().trim();

                if(TextUtils.isEmpty(price)) {
                    editText.setError("Price required");
                    return;
                }

                updateRecord(expId, date, price, cat);
                alertDialog.dismiss();
            }
        });

        //dialogBuilder.setTitle("Updating expense");


    }

    private boolean updateRecord(String expenseId, String date, String price, String category) {

        ExpenseRef = FirebaseDatabase.getInstance().getReference().child("expenses").child(currentUserID).child(expenseId);
            Expense expense = new Expense(expenseId, price, category, date);
            ExpenseRef.setValue(expense);

            Toast.makeText(getContext(), "Record Updated Successfully", Toast.LENGTH_SHORT).show();
        return true;
    }



    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView userDate, userPrice, userCategory;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            userDate = itemView.findViewById(R.id.text_view_date);
            userPrice = itemView.findViewById(R.id.text_view_price);
            userCategory = itemView.findViewById(R.id.text_view_category);
        }
    }



}
