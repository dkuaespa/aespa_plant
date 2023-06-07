package com.example.aespa_plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser == null) {
                    Intent intent = new Intent(MainActivity.this, Act_login.class);
                    startActivity(intent);
                    finish();
                } else {
                    checkkit();
                }
            }
        }, 4000);
    }

    public void checkkit(){
        String uid = currentUser.getUid();
        DatabaseReference keyRef = databaseReference.child("table").child(uid).child("key").child("kit_num");
        keyRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // "key" 노드가 존재함
                    Log.e("test",  uid+ "이미 키트값을 입력함 prof 로 이동");
                    Log.e("test",  "true임 prof로 이동");
                    Intent intent2 = new Intent(MainActivity.this, home_prof.class);
                    startActivity(intent2);
                    finish();

                } else {
                    Log.e("test",  uid+"키트값 존재하지 않음 emp로 이동");
                    Log.e("test",  "false임 emp로 이동");
                    Intent intent3 = new Intent(MainActivity.this, home_emp.class);
                    startActivity(intent3);
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }
}