package com.example.plant_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class activity_profileEdit2 extends AppCompatActivity {

    private ImageView save_btn;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit2);

        save_btn = (ImageView) findViewById(R.id.imageView17);
        back_btn = (ImageView) findViewById(R.id.imageView6);

        save_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_profileEdit2.this, activity_profileEdit.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_profileEdit2.this, activity_profileEdit.class);
                startActivity(intent);
            }
        });
    }
}