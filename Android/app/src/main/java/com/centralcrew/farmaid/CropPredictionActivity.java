package com.centralcrew.farmaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.centralcrew.farmaid.Adapters.AlternateCropAdapter;
import com.centralcrew.farmaid.Services.GetDataService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropPredictionActivity extends AppCompatActivity implements AlternateCropAdapter.RvListener {

    private static final String TAG = "CropPredictionActivity";
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private int district_encode;
    private String state, district, crop;
    private String yieldString;
    private ArrayList<BarEntry> values = new ArrayList<>();
    private int[] colorClassArrays;

    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    AlternateCropAdapter adapter = new AlternateCropAdapter(this,new ArrayList<String>(), this);

    BarChart pieChart;
    TextView labelTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_prediction);

        state = getIntent().getStringExtra("state");
        district = getIntent().getStringExtra("district");
        crop = getIntent().getStringExtra("crop");

        colorClassArrays = new int[]{getResources().getColor(R.color.yellow, getTheme()), getResources().getColor(R.color.colorAccent, getTheme())};

        Log.d(TAG, "onCreate: " + state + " " + district + " " + crop);

        pieChart = findViewById(R.id.bar_chart);
        labelTv = findViewById(R.id.label);

        labelTv.setText("Yield of " + crop + " in previous years vs in year " + 2020);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(adapter);

        loadData();
    }

    public void loadData(){

        databaseReference.child(state).child(crop).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                ArrayList<String> districts = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    districts.add(dataSnapshot.getKey());
                }
                district_encode = districts.indexOf(district);
                Log.d(TAG, "District: " + districts.indexOf(district));
                Query query = databaseReference.child(state).child(crop).child(district);
                query.orderByChild("Year").startAt(2012.0).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            values.add(new BarEntry((snapshot.child("Year").getValue(Float.class))
                                    , snapshot.child("Yield").getValue(Float.class)));
                        }
                        Log.d(TAG, "onDataChange: " + values);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

        databaseReference.child(state).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> crops = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(!dataSnapshot.getKey().equals(crop)){
                        crops.add(dataSnapshot.getKey());
                    }
                }
                adapter.setCrops(crops);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (state.equals("Uttar Pradesh")) {
            if (crop.equals("Rice")) {
                Call<String> call = service.getUpRice(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Wheat")) {
                Call<String> call = service.getUpWheat(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Sugarcane")) {
                Call<String> call = service.getUpSugarcane(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (state.equals("Maharashtra")) {
            if (crop.equals("Cotton")) {
                Call<String> call = service.getMhCotton(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Arhar")) {
                Call<String> call = service.getMhArhar(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Rice")) {
                Call<String> call = service.getMhRice(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Soyabean")) {
                Call<String> call = service.getMhSoyabean(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (state.equals("Haryana")) {
            if (crop.equals("Rice")) {
                Call<String> call = service.getHrRice(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Wheat")) {
                Call<String> call = service.getHrWheat(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else if(state.equals("Bihar")){
            if(crop.equals("Rice")){
                Call<String> call = service.getBrRice(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
            else if(crop.equals("Maize")){
                Call<String> call = service.getBrMaize(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(crop.equals("Wheat")){
                Call<String> call = service.getBrWheat(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else if(state.equals("Punjab")){
            if(crop.equals("Rice")){
                Call<String> call = service.getPbRice(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(crop.equals("Maize")){
                Call<String> call = service.getPbMaize(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(crop.equals("Wheat")){
                Call<String> call = service.getPbWheat(new APIObject(2020, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new BarEntry(2020,Float.parseFloat(yield)));
                        BarDataSet barDataSet = new BarDataSet(values,"");
                        barDataSet.setColors(colorClassArrays);
                        BarData pieData = new BarData(barDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void setCrop(String title) {
        crop = title;
        pieChart.clear();
        pieChart.notifyDataSetChanged();
        labelTv.setText("Yield of " + crop + " in previous years vs in year " + 2020);
        loadData();
    }
}