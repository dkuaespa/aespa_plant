package com.example.aespa_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class home_emp extends AppCompatActivity {

    private ImageView toprofile;
    private ImageView toshop;
    private ImageView todiary;
    private ImageView tomypage;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_emp);

        toprofile = (ImageView) findViewById(R.id.toprofile);
        toprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_emp.this, profileEdit.class);
                startActivity(intent);
            }
        });

        toshop = (ImageView) findViewById(R.id.toshop);
        toshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(home_emp.this, activity_shop.class);
                startActivity(intent1);
            }
        });

        todiary = (ImageView) findViewById(R.id.todiary);
        todiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(home_emp.this, activity_diary.class);
                startActivity(intent2);
            }
        });

        tomypage = (ImageView) findViewById(R.id.tomypage);
        tomypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(home_emp.this, activity_mypage.class);
                startActivity(intent3);
            }
        });

    }
}