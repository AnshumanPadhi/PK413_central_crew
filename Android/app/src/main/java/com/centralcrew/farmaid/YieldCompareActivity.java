package com.centralcrew.farmaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.centralcrew.farmaid.Adapters.CustomSpinnerAdapter;
import com.centralcrew.farmaid.Services.GetDataService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YieldCompareActivity extends AppCompatActivity {

    private String crop, state;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> states;
    private String state1, state2;
    private GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    private static final String TAG = "YieldComparison";
    private ArrayList<Entry> values = new ArrayList<>();
    private LineDataSet lineDataSet = new LineDataSet(null, null);
    private ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yield_compare);

        crop = getIntent().getStringExtra("crop");
        state = getIntent().getStringExtra("state");

        final Spinner stateSpinner1 = findViewById(R.id.state_one_spinner);
        final Spinner stateSpinner2 = findViewById(R.id.state_two_spinner);
        lineChart = findViewById(R.id.pie_chart);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        final LineDataSet set = new LineDataSet(values, "Data set");
        set.setFillAlpha(110);

        databaseReference.child(state).child(crop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                states = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    states.add(dataSnapshot.getKey());
                }
                CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(YieldCompareActivity.this, states);
                stateSpinner1.setAdapter(spinnerAdapter);

                CustomSpinnerAdapter spinnerAdapter1 = new CustomSpinnerAdapter(YieldCompareActivity.this, states);
                stateSpinner2.setAdapter(spinnerAdapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        stateSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state1 = adapterView.getSelectedItem().toString();
                if(state1.equals(state2)){
                    Toast.makeText(YieldCompareActivity.this, "Please select two different districts", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        stateSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state2 = adapterView.getSelectedItem().toString();
                if(state1.equals(state2)){
                    Toast.makeText(YieldCompareActivity.this, "Please select two different districts", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadData2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData(){

        if(state1 == null || state2 == null){
            return;
        }

        values = new ArrayList<>();
        dataSets = new ArrayList<>();

        databaseReference.child(state).child(crop).child(state1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    values.add(new Entry(snapshot.child("Year").getValue(Float.class)
                            , snapshot.child("Yield").getValue(Float.class)));
                    lineDataSet.setValues(values);
                    lineDataSet.setLabel("Yield of " + crop + " in " + state1);
                    dataSets.add(lineDataSet);
                    LineData lineData = new LineData(dataSets);
                    //lineChart.clear();
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadData2(){

        if(state1 == null || state2 == null){
            return;
        }

        values = new ArrayList<>();
        dataSets = new ArrayList<>();

        databaseReference.child(state).child(crop).child(state2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    values.add(new Entry(snapshot.child("Year").getValue(Float.class)
                            , snapshot.child("Yield").getValue(Float.class)));
                    lineDataSet.setValues(values);
                    lineDataSet.setLabel("Yield of " + crop + " in " + state1);
                    dataSets.add(lineDataSet);
                    LineData lineData = new LineData(dataSets);
                    lineChart.clear();
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}