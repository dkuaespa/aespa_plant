package com.example.plant_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button diary = (Button) findViewById(R.id.diary_btn);
        Button mypage = (Button) findViewById(R.id.mypage_btn);
        Button profile = (Button) findViewById(R.id.profile_btn);

        diary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, activity_diary.class);
                startActivity(intent);
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, activity_mypage.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, activity_profileEdit.class);
                startActivity(intent);
            }
        });
    }
}