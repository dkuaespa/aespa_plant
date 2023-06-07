package com.example.aespa_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class activity_sunflower extends AppCompatActivity {

    private ImageView buy;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunflower);

        buy = (ImageView) findViewById(R.id.buy);
        back = (ImageView) findViewById(R.id.toshop);
        String kind = "sunflower";

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_sunflower.this, activity_credit.class);
                intent.putExtra("text", kind);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_sunflower.this, activity_shop.class);
                startActivity(intent);
            }
        });
    }
}