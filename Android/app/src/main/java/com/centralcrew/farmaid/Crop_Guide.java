package com.centralcrew.farmaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Crop_Guide extends AppCompatActivity {

    TextView name,details;
    ImageView crop_imaage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop__guide);


        name=findViewById(R.id.name);
        crop_imaage=findViewById(R.id.crop_image);
        details=findViewById(R.id.crop_detail);




        Intent intent=getIntent();
        String Crop_Name=intent.getStringExtra("Crop_Name");

        name.setText(Crop_Name);
    }
}
