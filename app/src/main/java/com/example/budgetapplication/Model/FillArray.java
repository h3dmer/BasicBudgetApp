package com.example.budgetapplication.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FillArray {

    private DatabaseReference ref1, ref2, ref3, ref4;
    private FirebaseAuth mAuth;
    private String currentUserID;
    public float[] suma = new float[5];

    private float[] yData = {25f, 12f, 5f, 12f, 8f};
    private String[] category = {"Food", "Entertainment", "Clothes", "Travel", "Fuel"};
    PieChart pieChart;

    public FillArray() {

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        ref1 = FirebaseDatabase.getInstance().getReference();
        ref2 = ref1.child("expenses").child(currentUserID);

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String temp = (String) ds.child("category").getValue();
                    String number = (String) ds.child("price").getValue();
                    float numer = Float.parseFloat(number);



                    switch (temp) {
                        case "Food": {
                            suma[0] += numer;
                            Log.d("Food", "" + numer);
                            break;
                        }
                        case "Entertainment": {
                            suma[1] += numer;
                            Log.d("Enter", "" + numer);
                            break;
                        }
                        case "Clothes": {
                            suma[2] += numer;
                            Log.d("Clothes", "" + numer);
                            break;
                        }
                        case "Travel": {
                            suma[3] += numer;
                            Log.d("Travel", "" + numer);
                            break;
                        }
                        case "Fuel": {
                            suma[4] += numer;
                            Log.d("Fuel", "" + numer);
                            break;
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
