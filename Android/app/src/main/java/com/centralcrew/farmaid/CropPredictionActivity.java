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
import com.centralcrew.farmaid.Data.APIResponse;
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
    private String state, district, crop;
    private String yieldString;
    private ArrayList<BarEntry> values = new ArrayList<>();
    private int[] colorClassArrays;
    private int district_encode;
    String yield;

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
        district_encode = getIntent().getIntExtra("dist_enc",2);

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

        Log.d(TAG, "onCreate: " + district_encode);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        loadData();
    }

    public void loadData(){

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
                Call<APIResponse> call = service.getUpRice((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, district_encode + "response: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            } else if (crop.equals("Wheat")) {
                Call<APIResponse> call = service.getUpWheat((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            } else if (crop.equals("Sugarcane")) {
                Call<APIResponse> call = service.getUpSugarcane((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
        } else if (state.equals("Maharashtra")) {
            if (crop.equals("Cotton")) {
                Call<APIResponse> call = service.getMhCotton((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            } else if (crop.equals("Arhar")) {
                Call<APIResponse> call = service.getMhArhar((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            } else if (crop.equals("Rice")) {
                Call<APIResponse> call = service.getMhRice((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            } else if (crop.equals("Soyabean")) {
                Call<APIResponse> call = service.getMhSoyabean((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
        } else if (state.equals("Haryana")) {
            if (crop.equals("Rice")) {
                Call<APIResponse> call = service.getHrRice((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            } else if (crop.equals("Wheat")) {
                Call<APIResponse> call = service.getHrWheat((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
        }
        else if(state.equals("Bihar")){
            if(crop.equals("Rice")){
                Call<APIResponse> call = service.getBrRice((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
            else if(crop.equals("Maize")){
                Call<APIResponse> call = service.getBrMaize((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
            else if(crop.equals("Wheat")){
                Call<APIResponse> call = service.getBrWheat((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
        }
        else if(state.equals("Punjab")){
            if(crop.equals("Rice")){
                Call<APIResponse> call = service.getPbRice((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
            else if(crop.equals("Maize")){
                Call<APIResponse> call = service.getPbMaize((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);
                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
            else if(crop.equals("Wheat")){
                Call<APIResponse> call = service.getPbWheat((new APIObject(2020, district_encode)));
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.isSuccessful()){
                            APIResponse apiResponse = response.body();

                            yield = apiResponse.getResults();
                            yield = yield.substring(1);
                            yield = yield.substring(0, yield.length()-1);

                            Log.d(TAG, "onResponse: " + yield);

                            values.add(new BarEntry(2020,Float.parseFloat(yield)));
                            BarDataSet barDataSet = new BarDataSet(values,"");
                            barDataSet.setColors(colorClassArrays);
                            BarData pieData = new BarData(barDataSet);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    public void setCrop(String title) {
        crop = title;
        pieChart.clear();
        pieChart.invalidate();
        pieChart.notifyDataSetChanged();
        labelTv.setText("Yield of " + crop + " in previous years vs in year " + 2020);
        loadData();
    }
}