package com.example.aespa_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_credit extends AppCompatActivity {

    private ImageView okay;
    private TextView name;
    private TextView phone;
    private TextView address;
    private ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        okay = (ImageView) findViewById(R.id.okay);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        back = (ImageView) findViewById(R.id.toshop);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name!=null & phone!=null & address!=null){
                    Intent intent = new Intent(activity_credit.this, activity_shop.class);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_credit.this, activity_shop.class);
                startActivity(intent);
            }
        });

    }
}