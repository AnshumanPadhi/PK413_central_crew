package com.centralcrew.farmaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.centralcrew.farmaid.Services.GetDataService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropPredictionActivity extends AppCompatActivity {

    private static final String TAG = "CropPredictionActivity";
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private int district_encode;
    private float latestYear, latestYield;
    private String state, district, crop;
    private String yieldString;
    private ArrayList<PieEntry> values = new ArrayList<>();
    private int[] colorClassArrays;

    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    PieChart pieChart;
    TextView labelTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_prediction);

        state = getIntent().getStringExtra("state");
        district = getIntent().getStringExtra("district");
        crop = getIntent().getStringExtra("crop");
        latestYear = getIntent().getFloatExtra("latestYear",2020);
        latestYield = getIntent().getFloatExtra("latestYield", 0f);

        colorClassArrays = new int[]{getResources().getColor(R.color.pink, getTheme()), getResources().getColor(R.color.colorPrimary, getTheme())};

        Log.d(TAG, "onCreate: " + state + " " + district + " " + crop);

        pieChart = findViewById(R.id.pie_chart);
        labelTv = findViewById(R.id.label);

        pieChart.setHoleRadius(20);
        pieChart.setHoleColor(getResources().getColor(R.color.translucent));
        pieChart.setCenterTextColor(getResources().getColor(R.color.colorAccent));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
                    loadData(district_encode);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void loadData(final int district_encode){
        values.add(new PieEntry(latestYield, latestYear + ""));
        if (state.equals("Uttar Pradesh")) {
            if (crop.equals("Rice")) {
                Call<String> call = service.getUpRice(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Wheat")) {
                Call<String> call = service.getUpWheat(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Sugarcane")) {
                Call<String> call = service.getUpSugarcane(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (state.equals("Maharashtra")) {
            if (crop.equals("Cotton")) {
                Call<String> call = service.getMhCotton(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();
                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Arhar")) {
                Call<String> call = service.getMhArhar(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Rice")) {
                Call<String> call = service.getMhRice(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Soyabean")) {
                Call<String> call = service.getMhSoyabean(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (state.equals("Haryana")) {
            if (crop.equals("Rice")) {
                Call<String> call = service.getHrRice(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (crop.equals("Wheat")) {
                Call<String> call = service.getHrWheat(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
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
                Call<String> call = service.getBrRice(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
            else if(crop.equals("Maize")){
                Call<String> call = service.getBrMaize(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(crop.equals("Wheat")){
                Call<String> call = service.getBrWheat(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
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
                Call<String> call = service.getPbRice(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(crop.equals("Maize")){
                Call<String> call = service.getPbMaize(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(crop.equals("Wheat")){
                Call<String> call = service.getPbWheat(new APIObject(2021, district_encode));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String yield = response.body().substring(1);
                        yield = yield.substring(0, yield.length()-1);

                        Log.d(TAG, "onResponse: yield " + Float.parseFloat(yield));

                        values.add(new PieEntry(Float.parseFloat(yield), 2021 + ""));
                        PieDataSet pieDataSet = new PieDataSet(values,"");
                        pieDataSet.setColors(colorClassArrays);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.notifyDataSetChanged();

                        labelTv.setText("Yield of " + crop + " in year " + (int) latestYear + " vs in year " + 2021);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CropPredictionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}