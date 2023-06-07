package com.example.aespa_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class home_emp extends AppCompatActivity {
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = database.getReference();

    private ImageView toprofile;
    private ImageView toshop;
    private ImageView todiary;
    private ImageView tomypage;
    private TextView uname;

    private DatabaseReference mDatabase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_emp);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uname = (TextView) findViewById(R.id.username);

        String uid = user.getUid();

        mDatabase.child("table").child(uid).child("uid").child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //DataSnapshot snapshot = task.getResult();
                    Log.d("firebase!", String.valueOf(task.getResult().getValue()));
                    uname.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        databaseReference.child("table").child(uid).child("plant_kind").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    toprofile = (ImageView) findViewById(R.id.toprofile);

                    toprofile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("test",  "식물 이미 구매함");
                            Intent intent = new Intent(home_emp.this, profileEdit.class);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    toprofile = (ImageView) findViewById(R.id.toprofile);

                    toprofile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(home_emp.this, "식물을 구매하지 않음. 쇼핑먼저 plz", Toast.LENGTH_SHORT).show();
                            Log.e("test",  "식물을 구매하지 않음 쇼핑먼저");


                        }

                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
        toshop = (ImageView) findViewById(R.id.toshop);
        toshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_emp.this, activity_shop.class);
                startActivity(intent);
            }
        });

    }


}