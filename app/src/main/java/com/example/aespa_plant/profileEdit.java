package com.example.aespa_plant;
import static android.text.TextUtils.isEmpty;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


public class profileEdit extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = database.getReference();

    private ImageView back;
    private ImageView save;
    private TextView uname;

    ImageView plant_happy;
    ImageView plant_sad;
    ImageView flower_happy;
    ImageView flower_sad;

    ImageView textview1; //위의 상단바
    TextView textview2; //oo님의 식물
    TextView textview3;
    ImageView white_back; //흰색 뒷배경

    TextView cname;
    TextView keyvalue;

    TextView saved_name;
    TextView saved_key;
    TextView txt;
    TextView txt2;

    private String char_num;
    private String key;
    private String c_name;

    int casenum = 0;

    String uid = user.getUid();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        textview2 = (TextView)findViewById(R.id.textView2);
        textview1 = (ImageView) findViewById(R.id.textView1);
        plant_happy = (ImageView) findViewById(R.id.plant_happy2);
        plant_sad = (ImageView) findViewById(R.id.plant_sad2);
        flower_happy = (ImageView) findViewById(R.id.flower_happy2);
        flower_sad = (ImageView) findViewById(R.id.flower_sad2);

        textview3 = (TextView) findViewById(R.id.textView3);
        white_back = (ImageView) findViewById(R.id.white_back);

        cname = (TextView) findViewById(R.id.cname);
        keyvalue = (TextView) findViewById(R.id.keyvalue);

        saved_name = (TextView) findViewById(R.id.saved_name);
        saved_key = (TextView) findViewById(R.id.saved_key);
        txt = (TextView) findViewById(R.id.txt);
        txt2 = (TextView) findViewById(R.id.txt2);

        //보일지말지
        //등록하기전
        textview3.setVisibility(View.VISIBLE); //캐릭터를 등록해주세요
        white_back.setVisibility(View.VISIBLE); //흰색 배경

        txt.setVisibility(View.INVISIBLE); //등록 후 키트 번호
        txt2.setVisibility(View.INVISIBLE); //등록 후 캐릭터 이름

        plant_happy.setVisibility(View.INVISIBLE);
        plant_sad.setVisibility(View.INVISIBLE);
        flower_happy.setVisibility(View.INVISIBLE);
        flower_sad.setVisibility(View.INVISIBLE);


        cname.setVisibility(View.VISIBLE); //캐릭터 네임 입력해야함 (이름 변경 불가)
        keyvalue.setVisibility(View.VISIBLE); //키트 번호 입력해야함 (입력 후 변경 불가)

        saved_name.setVisibility(View.INVISIBLE); //? 이건 뭐지?
        saved_key.setVisibility(View.INVISIBLE);  //? 이건 또한 뭐지???

        uname = (TextView) findViewById(R.id.u_name);

        Button click_btn = (Button) findViewById(R.id.click);
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
                DatabaseReference keyRef = databaseReference.child("table").child(uid).child("key").child("kit_num");
                keyRef.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // "key" 노드가 존재함
                            Intent intent = new Intent(profileEdit.this, home_prof.class);
                            startActivity(intent);


                        } else {
                            Intent intent = new Intent(profileEdit.this, home_emp.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }

                });
            }
        });

        save=(ImageView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String keyvalue = ((EditText)findViewById(R.id.keyvalue)).getText().toString();
                int kit_num =Integer.valueOf(keyvalue);
                String cname =((EditText) findViewById(R.id.cname)).getText().toString();

                if ( !keyvalue.isEmpty() && !cname.isEmpty()){
                    //Toast.makeText(profileEdit.this, "키값은"+ kit_num + "입니다", Toast.LENGTH_LONG).show();
                    //키트 넘버가 유효한지 검사한다.
                    keycheck(kit_num, keyvalue, cname);

                }
                else{
                    Toast.makeText(profileEdit.this, "값을 모두 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });


        //닉네임 가져오기
        databaseReference.child("table").child(uid).child("uid").child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

        databaseReference.child("table").child(uid).child("char").child("char_num").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    char_num = valueOf(task.getResult().getValue());
                    //DataSnapshot snapshot = task.getResult();
                    Log.d("firebase!", valueOf(task.getResult().getValue()));
                    if(char_num.equals("1")){
                        plant_happy.setVisibility(View.VISIBLE);
                        plant_sad.setVisibility(View.INVISIBLE);
                        flower_happy.setVisibility(View.INVISIBLE);
                        flower_sad.setVisibility(View.INVISIBLE);
                        textview3.setVisibility(View.INVISIBLE);
                        white_back.setVisibility(View.INVISIBLE);
                    } else if(char_num.equals("2")){
                        plant_happy.setVisibility(View.INVISIBLE);
                        plant_sad.setVisibility(View.VISIBLE);
                        flower_happy.setVisibility(View.INVISIBLE);
                        flower_sad.setVisibility(View.INVISIBLE);
                        textview3.setVisibility(View.INVISIBLE);
                        white_back.setVisibility(View.INVISIBLE);
                    } else if(char_num.equals("3")){
                        plant_happy.setVisibility(View.INVISIBLE);
                        plant_sad.setVisibility(View.INVISIBLE);
                        flower_happy.setVisibility(View.VISIBLE);
                        flower_sad.setVisibility(View.INVISIBLE);
                        textview3.setVisibility(View.INVISIBLE);
                        white_back.setVisibility(View.INVISIBLE);
                    } else if(char_num.equals("4")){
                        plant_happy.setVisibility(View.INVISIBLE);
                        plant_sad.setVisibility(View.INVISIBLE);
                        flower_happy.setVisibility(View.INVISIBLE);
                        flower_sad.setVisibility(View.VISIBLE);
                        textview3.setVisibility(View.INVISIBLE);
                        white_back.setVisibility(View.INVISIBLE);
                    } else{
                        plant_happy.setVisibility(View.INVISIBLE);
                        plant_sad.setVisibility(View.INVISIBLE);
                        flower_happy.setVisibility(View.INVISIBLE);
                        flower_sad.setVisibility(View.INVISIBLE);
                        textview3.setVisibility(View.VISIBLE);
                        white_back.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        databaseReference.child("table").child(uid).child("key").child("kit_num").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    key = valueOf(task.getResult().getValue());
                    //DataSnapshot snapshot = task.getResult();
                    Log.d("firebase!", valueOf(task.getResult().getValue()));
                    if(!key.equals("null")){
                        casenum = 1;
                        keyvalue.setVisibility(View.INVISIBLE);
                        saved_key.setText(key);
                        saved_key.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        save.setVisibility(View.INVISIBLE);
                    }  else{
                        keyvalue.setVisibility(View.VISIBLE);
                        saved_key.setVisibility(View.INVISIBLE);
                        txt2.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });



        databaseReference.child("table").child(uid).child("key").child("cname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    c_name = valueOf(task.getResult().getValue());
                    //DataSnapshot snapshot = task.getResult();
                    Log.d("firebase!", valueOf(task.getResult().getValue()));
                    if(!c_name.equals("null")){
                        casenum = 1;
                        cname.setVisibility(View.INVISIBLE);
                        saved_name.setText(c_name);
                        saved_name.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.VISIBLE);
                        save.setVisibility(View.INVISIBLE);
                    }  else{
                        cname.setVisibility(View.VISIBLE);
                        saved_name.setVisibility(View.INVISIBLE);
                        txt.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

    }


    public void tablekey(String keyvalue, String cname){
        tablekey tablekey = new tablekey(keyvalue,cname);
        databaseReference.child("table").child(uid).child("key").setValue(tablekey);
    }

    public void keycheck(int kit_number, String keyvalue, String cname) {
        databaseReference.child("User").child("kit_num").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스에서 아두이노 kit_n 값 가져오기
                int kit_n = (int) snapshot.getValue(Integer.class);
                if (kit_n == kit_number) {
                    Toast.makeText(profileEdit.this, "키트 번호 인증완료", Toast.LENGTH_LONG).show();
                    Log.v("test", "같음");
                    //같으면 하위값 가져오기
                    tablekey(keyvalue, cname);
                    Intent intent1 = new Intent(profileEdit.this, home_prof.class);
                    startActivity(intent1);
                    //addmoisture();
                } else {
                    Toast.makeText(profileEdit.this, "없는 키트 번호 입니다", Toast.LENGTH_LONG).show();
                    Log.v("test", kit_n + "같지않음");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}