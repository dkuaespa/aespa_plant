package com.example.aespa_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_credit extends AppCompatActivity {

    private ImageView okay;
    private TextView name;
    private TextView phone;
    private TextView address;
    private ImageView back;
    private CheckBox creditcard;
    private CheckBox bank;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = database.getReference();


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

        creditcard = (CheckBox) findViewById(R.id.creditcard);
        bank = (CheckBox) findViewById(R.id.bank);
        Intent input = getIntent();

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("") && !phone.getText().toString().equals("") && !address.getText().toString().equals("")) {
                    if(creditcard.isChecked()==true && bank.isChecked()==true){
                        Toast.makeText(activity_credit.this, "체크 박스 중 하나만 선택해주세요.", Toast.LENGTH_LONG).show();

                    } else if(creditcard.isChecked()==true || bank.isChecked()==true){
                        Intent intent = new Intent(activity_credit.this, activity_shop.class);
                        String text = input.getStringExtra("text");
                        Log.v("test",text+ "값 전달됨 ");
                        tableshop(text);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(activity_credit.this, "체크 박스를 체크하세요.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(activity_credit.this, "입력창을 확인하세요.", Toast.LENGTH_LONG).show();
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
    public void tableshop(String text){
        String uid = user.getUid();
        tableshop tableshop = new tableshop(text);
        databaseReference.child("table").child(uid).child("plant_kind").setValue(tableshop);
    }
}