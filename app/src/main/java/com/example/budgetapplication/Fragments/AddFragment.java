package com.example.budgetapplication.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetapplication.Model.Expense;
import com.example.budgetapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AddFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private TextView theDate;
    private EditText editPrice;
    private ImageButton btnGoCalendar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btn;
    private Spinner spinner;
    private ArrayAdapter<String> dataAdapter;


    private DatabaseReference databaseExpenses;
    private DatabaseReference logReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String currentUserId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add, container, false);



        return rootView;
        //return inflater.inflate(R.layout.fragment_add, container, false);
    }

    public Spinner makeSpinner(View view) {

        // Spinner element
        spinner = (Spinner) view.findViewById(R.id.spinner);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Food");
        categories.add("Fuel");
        categories.add("Entertainment");
        categories.add("Clothes");
        categories.add("Travel");

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categories);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        return spinner;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        /*
        // Spinner element
        spinner = (Spinner) view.findViewById(R.id.spinner);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Food");
        categories.add("Fuel");
        categories.add("Entertainment");
        categories.add("Clothes");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categories);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        */

        spinner = makeSpinner(view);

        theDate = (TextView) view.findViewById(R.id.calText);
        btnGoCalendar = (ImageButton) view.findViewById(R.id.calButton);

        //FIREBASE

        databaseExpenses = FirebaseDatabase.getInstance().getReference("expenses");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();


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

        List<Expense> expenses;

        btn = (Button) view.findViewById(R.id.addExpense);
        editPrice = (EditText) view.findViewById(R.id.editPrice);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addExpense();
                editPrice.setText(" ");
                theDate.setText("Date");

            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addExpense() {
        String price = editPrice.getText().toString().trim();
        String category = spinner.getSelectedItem().toString();
        String date = theDate.getText().toString();

        if(!TextUtils.isEmpty(price)) {

            String id = databaseExpenses.push().getKey();

            Expense expense = new Expense(id,price, category, date);

            databaseExpenses.child(currentUserId).child(id).setValue(expense);
            Toast.makeText(getContext(), "Expense added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "You should enter a price", Toast.LENGTH_SHORT).show();
        }

    }
}
