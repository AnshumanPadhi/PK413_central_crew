package com.centralcrew.farmaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

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
import java.util.Timer;
import java.util.TimerTask;

public class OptionPage extends AppCompatActivity{

    private String state,dist,crop;
    private Spinner spinner_crop;
    private LineChart lineChart;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<Entry> values = new ArrayList<>();
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();

    private String TAG = "OptionPage";
    private float latestYear, latestYield;
    private int district_encode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_page);

        Intent intent=getIntent();
        state=intent.getStringExtra("State");
        dist=intent.getStringExtra("District");
        crop = intent.getStringExtra("Crop");
        district_encode = intent.getIntExtra("dist_enc", 0);

        CardView yieldCompareCv = findViewById(R.id.yield_compare_Cv);
        CardView cropGuideCv = findViewById(R.id.crop_guide_Cv);
        CardView smartYieldCalculatorCv = findViewById(R.id.smart_yield_calculator_Cv);
        CardView cropPredictionCv = findViewById(R.id.crop_predict_Cv);
        lineChart = findViewById(R.id.line_chart);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        final LineDataSet set = new LineDataSet(values, "Data set");
        set.setFillAlpha(110);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                databaseReference.child(state).child(crop).child(dist).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        values = new ArrayList<>();
                        Log.d(TAG, "onDataChange: " + dist);
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            values.add(new Entry(dataSnapshot.child("Year").getValue(Float.class)
                                    , dataSnapshot.child("Yield").getValue(Float.class)));
                        }
                        latestYear = values.get(values.size() - 1).getX();
                        latestYield = values.get(values.size() - 1).getY();
                        Log.d(TAG, "onDataChange: " + values);
                        lineDataSet.setValues(values);
                        lineDataSet.setLabel("Yield of " + crop);
                        dataSets.clear();
                        dataSets.add(lineDataSet);
                        LineData lineData = new LineData(dataSets);
                        lineChart.clear();
                        lineChart.setData(lineData);
                        lineChart.invalidate();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }}, 1500, 10000);

        cropPredictionCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OptionPage.this, CropPredictionActivity.class);
                i.putExtra("state", state);
                i.putExtra("district", dist);
                i.putExtra("crop", crop);
                i.putExtra("dist_enc", district_encode);
                i.putExtra("latestYear", latestYear);
                i.putExtra("latestYield", latestYield);
                startActivity(i);
            }
        });

        yieldCompareCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OptionPage.this, YieldCompareActivity.class);
                i.putExtra("state", state);
                i.putExtra("crop", crop);
                startActivity(i);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(state + ", " + dist);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
