package com.example.aespa_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_shop extends AppCompatActivity {

    private ImageView tomato_pic;
    private ImageView sunflower_pic;
    private ImageView cosmos_pic;
    private ImageView bean_pic;
    private ImageView back;

    private TextView tomatokit;
    private TextView sunflowerkit;
    private TextView cosmoskit;
    private TextView beankit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        tomato_pic = (ImageView) findViewById(R.id.tomato);
        sunflower_pic = (ImageView) findViewById(R.id.sunflower);
        cosmos_pic = (ImageView) findViewById(R.id.cosmos);
        bean_pic = (ImageView) findViewById(R.id.bean);
        back = (ImageView) findViewById(R.id.toshop);

        tomatokit = (TextView) findViewById(R.id.tomatokit);
        sunflowerkit = (TextView) findViewById(R.id.sunflowerkit);
        cosmoskit = (TextView) findViewById(R.id.cosmoskit);
        beankit = (TextView) findViewById(R.id.beankit);

        tomato_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_tomato.class);
                startActivity(intent);
            }
        });
        tomatokit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_tomato.class);
                startActivity(intent);
            }
        });

        sunflower_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_sunflower.class);
                startActivity(intent);
            }
        });
        sunflowerkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_sunflower.class);
                startActivity(intent);
            }
        });

        cosmos_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_cosmos.class);
                startActivity(intent);
            }
        });
        cosmoskit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_cosmos.class);
                startActivity(intent);
            }
        });

        bean_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_bean.class);
                startActivity(intent);
            }
        });
        beankit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, activity_bean.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_shop.this, home_emp.class);
                startActivity(intent);
            }
        });
    }
}