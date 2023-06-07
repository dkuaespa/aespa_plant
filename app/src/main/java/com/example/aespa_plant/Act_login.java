package com.example.aespa_plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Act_login extends AppCompatActivity {
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = database.getReference();

    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;



    private ImageView sign;
    private ImageButton login;
    private EditText editTextEmail;
    private EditText editTextPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_login);

        firebaseAuth = FirebaseAuth.getInstance();
/*
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), home_prof.class);
            startActivity(intent);
            finish();
        }
*/
        editTextEmail = (EditText)findViewById(R.id.id);
        editTextPassword =(EditText)findViewById(R.id.password);

        sign = (ImageView) findViewById(R.id.sign_gobtn);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Act_login.this, Act_singup.class);
                startActivity(intent);
            }
        });

        login = (ImageButton)findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                }
                else{
                    Toast.makeText(Act_login.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }

        });
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.e("test",  "현재 로그인되어 있는지 체크 ok");
                    checkkit();

                } else {
                }
            }
        };

    }



    public void loginUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //로그인 성공
                    Toast.makeText(Act_login.this,"로그인 성공", Toast.LENGTH_SHORT).show();
                    firebaseAuth.addAuthStateListener(firebaseAuthListener);


                }
                else{
                    Toast.makeText(Act_login.this, "아이디 또는 비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();

                }
            }

        });

    }

    public void checkkit(){
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference keyRef = databaseReference.child("table").child(uid).child("key").child("kit_num");
        keyRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // "key" 노드가 존재함
                    Log.e("test",  uid+ "이미 키트값을 입력함 prof 로 이동");
                    Log.e("test",  "true임 prof로 이동");
                    Intent intent2 = new Intent(Act_login.this, home_prof.class);
                    startActivity(intent2);
                    finish();


                } else {
                    Log.e("test",  uid+"키트값 존재하지 않음 emp로 이동");
                    Log.e("test",  "false임 emp로 이동");
                    Intent intent3 = new Intent(Act_login.this, home_emp.class);
                    startActivity(intent3);
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

}