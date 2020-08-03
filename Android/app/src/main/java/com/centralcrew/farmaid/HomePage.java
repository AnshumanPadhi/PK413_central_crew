package com.centralcrew.farmaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.centralcrew.farmaid.Adapters.CustomSpinnerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity{

    private String state, dist, crop;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String TAG = "HomePage";
    private Spinner stateSpinner, districtSpinner, cropSpinner;
    private ProgressBar progressBar;

    private int district_encode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        stateSpinner = findViewById(R.id.spinner);
        districtSpinner = findViewById(R.id.spinner_dist);
        cropSpinner = findViewById(R.id.spinner_season);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> states = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    states.add(dataSnapshot.getKey());
                }
                Log.d(TAG, "States are:"+ states);
                state = states.get(0);
                CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(HomePage.this, states);
                stateSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onStateSelected: " + state);
                cropSpinner.setVisibility(View.VISIBLE);
                loadCrops();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                crop = adapterView.getItemAtPosition(i).toString();
                districtSpinner.setVisibility(View.VISIBLE);
                loadDistricts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dist = adapterView.getItemAtPosition(i).toString();
                district_encode = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void loadCrops(){
        databaseReference.child(state).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> crops = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    crops.add(dataSnapshot.getKey());
                }
                Log.d(TAG, "Crops are:"+ crops);
                CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(HomePage.this, crops);
                cropSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadDistricts(){
        databaseReference.child(state).child(crop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> districts = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    districts.add(dataSnapshot.getKey());
                }
                Log.d(TAG, "Districts are:"+ districts);
                CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(HomePage.this, districts);
                districtSpinner.setAdapter(spinnerAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void submit_detail(View view) {
        Intent intent=new Intent(HomePage.this,OptionPage.class);
        intent.putExtra("State",state);
        intent.putExtra("District",dist);
        intent.putExtra("Crop", crop);
        intent.putExtra("dist_enc", district_encode);
        startActivity(intent);
    }
}

