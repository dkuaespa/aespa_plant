package com.example.aespa_plant;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class profileEdit2 extends AppCompatActivity {

    private ImageView save_btn;
    private ImageView back_btn;
    private ImageView back;

    private ImageView plant_happy;
    private ImageView plant_sad;
    private ImageView flower_happy;
    private ImageView flower_sad;

    ImageView imageView4;
    ImageView imageView4_change;
    ImageView imageView22;
    ImageView imageView22_change;
    ImageView imageView14;
    ImageView imageView14_change;
    ImageView imageView23;
    ImageView imageView23_change;

    int imageIndex1 = 0;
    int imageIndex2 = 0;
    int imageIndex3 = 0;
    int imageIndex4 = 0;

    //private FirebaseStorage storage:

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit2);

        save_btn = (ImageView) findViewById(R.id.imageView17);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        plant_happy = (ImageView) findViewById(R.id.plant_happy);
        plant_sad = (ImageView) findViewById(R.id.plant_sad);
        flower_happy = (ImageView) findViewById(R.id.flower_happy);
        flower_sad = (ImageView) findViewById(R.id.flower_sad);


        imageView4 = findViewById(R.id.toshop);
        imageView4_change = findViewById(R.id.imageView4_change);
        imageView22 = findViewById(R.id.imageView22);
        imageView22_change = findViewById(R.id.imageView22_change);
        imageView14 = findViewById(R.id.imageView14);
        imageView14_change = findViewById(R.id.imageView14_change);
        imageView23 = findViewById(R.id.imageView23);
        imageView23_change = findViewById(R.id.imageView23_change);

        imageView4_change.setVisibility(View.INVISIBLE);
        imageView22_change.setVisibility(View.INVISIBLE);
        imageView14_change.setVisibility(View.INVISIBLE);
        imageView23_change.setVisibility(View.INVISIBLE);


        save_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileEdit2.this, profileEdit.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileEdit2.this, profileEdit.class);
                startActivity(intent);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(imageIndex1 == 0){
                    imageView4_change.setVisibility(View.VISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView14_change.setVisibility(View.INVISIBLE);
                    imageView14.setVisibility(View.VISIBLE);
                    imageView22_change.setVisibility(View.INVISIBLE);
                    imageView22.setVisibility(View.VISIBLE);
                    imageView23_change.setVisibility(View.INVISIBLE);
                    imageView23.setVisibility(View.VISIBLE);
                    imageIndex1 = 1;
                }

                else if(imageIndex1 == 1){
                    imageView4_change.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                    imageIndex1 = 0;
                }
            }
        });

        imageView22.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(imageIndex2 == 0){
                    imageView22_change.setVisibility(View.VISIBLE);
                    imageView22.setVisibility(View.INVISIBLE);
                    imageView14_change.setVisibility(View.INVISIBLE);
                    imageView14.setVisibility(View.VISIBLE);
                    imageView4_change.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                    imageView23_change.setVisibility(View.INVISIBLE);
                    imageView23.setVisibility(View.VISIBLE);
                    imageIndex2 = 1;
                }

                else if(imageIndex2 == 1){
                    imageView22_change.setVisibility(View.INVISIBLE);
                    imageView22.setVisibility(View.VISIBLE);
                    imageIndex2 = 0;
                }
            }
        });

        imageView14.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(imageIndex3 == 0){
                    imageView14_change.setVisibility(View.VISIBLE);
                    imageView14.setVisibility(View.INVISIBLE);
                    imageView4_change.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                    imageView22_change.setVisibility(View.INVISIBLE);
                    imageView22.setVisibility(View.VISIBLE);
                    imageView23_change.setVisibility(View.INVISIBLE);
                    imageView23.setVisibility(View.VISIBLE);
                    imageIndex3 = 1;
                }

                else if(imageIndex3 == 1){
                    imageView14_change.setVisibility(View.INVISIBLE);
                    imageView14.setVisibility(View.VISIBLE);
                    imageIndex3 = 0;
                }
            }
        });

        imageView23.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(imageIndex4 == 0){
                    imageView23_change.setVisibility(View.VISIBLE);
                    imageView23.setVisibility(View.INVISIBLE);
                    imageView14_change.setVisibility(View.INVISIBLE);
                    imageView14.setVisibility(View.VISIBLE);
                    imageView22_change.setVisibility(View.INVISIBLE);
                    imageView22.setVisibility(View.VISIBLE);
                    imageView4_change.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                    imageIndex4 = 1;
                }

                else if(imageIndex4 == 1){
                    imageView23_change.setVisibility(View.INVISIBLE);
                    imageView23.setVisibility(View.VISIBLE);
                    imageIndex4 = 0;
                }
            }


        });
    }


}