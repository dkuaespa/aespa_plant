package com.example.aespa_plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_mypage extends AppCompatActivity {

    public FirebaseAuth mAuth;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ImageView back;
    private ImageView logout;

    private ImageView tomato;
    private ImageView cosmos;
    private ImageView bean;
    private ImageView sunflower;
    private ImageView nothing;
    private TextView plantname;




    private TextView email;
    private TextView uname;

    //private DatabaseReference mDatabase;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        email = (TextView) findViewById(R.id.email);
        uname = (TextView) findViewById(R.id.nickname);

        plantname = (TextView)findViewById(R.id.nickname2);

        tomato = (ImageView) findViewById(R.id.tomato);
        sunflower = (ImageView) findViewById(R.id.sunflower);
        cosmos = (ImageView) findViewById(R.id.cosmos);
        bean = (ImageView) findViewById(R.id.bean);
        nothing = (ImageView) findViewById(R.id.nothing);

        tomato.setVisibility(View.INVISIBLE);
        sunflower.setVisibility(View.INVISIBLE);
        cosmos.setVisibility(View.INVISIBLE);
        bean.setVisibility(View.INVISIBLE);
        nothing.setVisibility(View.INVISIBLE);


        String uid = mAuth.getCurrentUser().getUid();
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

        mDatabase.child("table").child(uid).child("uid").child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //DataSnapshot snapshot = task.getResult();
                    Log.d("firebase!", String.valueOf(task.getResult().getValue()));
                    email.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });


        mDatabase.child("table").child(uid).child("plant_kind").child("shop_num").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //DataSnapshot snapshot = task.getResult();
                    DataSnapshot dataSnapshot = task.getResult();
                    if(dataSnapshot.exists()) {
                        String kind =dataSnapshot.getValue(String.class);
                        Log.v("test", kind+"종류 출력");
                        plantname.setText(kind+"를 구매하셨습니다");
                        if(kind.equals("bean")){
                            bean.setVisibility(View.VISIBLE);
                        }
                        else if(kind.equals("sunflower")){
                            sunflower.setVisibility(View.VISIBLE);
                        }
                        else if(kind.equals("cosmos")){
                            cosmos.setVisibility(View.VISIBLE);
                        }
                        else if(kind.equals("tomato")){
                            tomato.setVisibility(View.VISIBLE);
                        }
                        else {
                            Log.v("test", "img shop error");
                        }
                    }
                    else{
                        nothing.setVisibility(View.VISIBLE);
                        Log.v("test", "낫띵");
                    }

                }
            }
        });

        back = (ImageView) findViewById(R.id.toshop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_mypage.this, home_prof.class);
                startActivity(intent);
            }
        });

        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(activity_mypage.this, Act_login.class);
                startActivity(intent);
                onStop();
                finish();


            }
        });
    }
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}