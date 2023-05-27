package com.example.aespa_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class profileEdit extends AppCompatActivity {

    private ImageView back;
    private ImageView save;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Button click_btn = (Button) findViewById(R.id.button3);
        click_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileEdit.this, profileEdit2.class);
                startActivity(intent);
            }
        });

        back = (ImageView) findViewById(R.id.toshop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileEdit.this, home_prof.class);
                startActivity(intent);
            }
        });

        save=(ImageView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(profileEdit.this, home_prof.class);
                startActivity(intent1);
            }
        });
    }
}