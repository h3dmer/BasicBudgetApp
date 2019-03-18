package com.example.budgetapplication.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetapplication.MainActivity;
import com.example.budgetapplication.Model.Expense;
import com.example.budgetapplication.Model.FillArray;
import com.example.budgetapplication.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ChartFragment extends Fragment {

    private View ChartFragmentView;
    private ShowFragment sf = new ShowFragment();
    private DatabaseReference ref1, ref2, ref3, ref4;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private List<Expense> ExpenseList;
    private float[] suma = new float[5];

    private float[] yData = {332.60f, 800.14f, 440.5f, 1000f, 606f};
    private String[] category = {"Food", "Entertainment", "Clothes", "Travel", "Fuel"};
    PieChart pieChart;
    ArrayList<PieEntry> yEntrys = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final float[] tmp = new float[]{0,0,0,0,0};

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
                            tmp[0] += numer;
                            Log.d("Food", "" + numer);
                            break;
                        }
                        case "Entertainment": {
                            tmp[1] += numer;
                            Log.d("Enter", "" + numer);
                            break;
                        }
                        case "Clothes": {
                            tmp[2] += numer;
                            Log.d("Clothes", "" + numer);
                            break;
                        }
                        case "Travel": {
                            tmp[3] += numer;
                            Log.d("Travel", "" + numer);
                            break;
                        }
                        case "Fuel": {
                            tmp[4] += numer;
                            Log.d("Fuel", "" + numer);
                            break;
                        }
                        default: {
                            break;
                        }

                    }

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setSuma(tmp);

    }



    public void setSuma(float[] cos) {
        suma = cos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ChartFragmentView = inflater.inflate(R.layout.fragment_chart, container, false);


        /*mAuth = FirebaseAuth.getInstance();
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
                        default: {
                            break;
                        }

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



        return ChartFragmentView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = ChartFragmentView.findViewById(R.id.idPieChart);
        Description description = new Description();
        description.setTextSize(20f);
        description.setText("All money that we spent");
        description.setTextColor(Color.WHITE);
        pieChart.setRotationEnabled(true);
        pieChart.setDescription(description);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("All Expenses");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);

        addDataSet(suma);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("Chart Fragment","onValueSelected: Value select from chart.");
                Log.d("Chart Fragment", "onValueSelected: " + e.toString());
                Log.d("Chart Fragment", "onValueSelected: " + h.toString());

                /*int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1+7);

                for(int i=0; i < suma.length; i++) {
                    if(suma[i] == Float.parseFloat(sales)) {
                        pos1 = i;
                        break;
                    }
                }

                String kategoria = category[pos1];
                Toast.makeText(getContext(), "Category " + kategoria + "\n" + "Value: " + sales, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected() {

            }
        });

        /*Fragment frg = null;
        frg = getFragmentManager().findFragmentByTag("ChartFragment");
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/



    }



    private void addDataSet(float[] suma) {

        Log.d(TAG, "addDataSet started");
        suma = yData;
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i=0; i < suma.length; i++) {
            yEntrys.add(new PieEntry(suma[i] , category[i]));
            Log.d("ADD" + i, "" + suma[i]);
        }

        /*for(int i=0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i] , i));
        }*/

        for(int i=0; i < category.length; i++) {
            xEntrys.add(category[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, getString(R.string.chartCategory));
        pieDataSet.setSliceSpace(4);
        pieDataSet.setValueTextSize(20);


        //kolorki
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();


    }

}
