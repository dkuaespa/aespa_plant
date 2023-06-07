package com.example.aespa_plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;

public class Act_singup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_singup);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.checkButton).setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.checkButton:
                    signUp();
                    break;
            }
        }
    };

    private void signUp() {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String id = ((EditText) findViewById(R.id.id)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.password_check)).getText().toString();


        if (id.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
            if (password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Act_singup.this, "회원가입에 성공!", Toast.LENGTH_SHORT).show();
                            tablename(name,id,password);
                            Intent intent = new Intent(Act_singup.this, Act_login.class);
                            startActivity(intent);
                        } else {
                            if (task.getException().toString() != null) {
                                Toast.makeText(Act_singup.this, "회원가입에 실패함", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(Act_singup.this, " 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Act_singup.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    public void tablename(String name, String email, String password){
        String uid = mAuth.getCurrentUser().getUid();
        tablename tablename = new tablename(name, email, password);
        databaseReference.child("table").child(uid).child("uid").setValue(tablename);
    }
}