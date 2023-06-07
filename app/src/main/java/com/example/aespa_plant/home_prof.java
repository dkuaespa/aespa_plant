package com.example.aespa_plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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

import java.util.Timer;
import java.util.TimerTask;

public class home_prof<mAuth> extends AppCompatActivity {

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = database.getReference();

    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    private ImageView toshop;
    private ImageView todiary;
    private ImageView tomypage;
    private TextView uname;
    private TextView water;
    private TextView state;

    private Button profileBtn;
    private Button button;
    private String char_num;

    private final String DEFAULT = "DEFALUT";

    String character;

    ImageView plant_happy;
    ImageView plant_sad;
    ImageView flower_happy;
    ImageView flower_sad;
    ImageView nothing;

    int status = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_prof);

        mAuth = FirebaseAuth.getInstance();
        uname = (TextView)findViewById(R.id.uname);

        water =(TextView)findViewById(R.id.water_data);
        state =(TextView)findViewById(R.id.state_data);

        plant_happy = (ImageView) findViewById(R.id.plant_happy3);
        plant_sad = (ImageView) findViewById(R.id.plant_sad3);
        flower_happy = (ImageView) findViewById(R.id.flower_happy3);
        flower_sad = (ImageView) findViewById(R.id.flower_sad3);
        nothing = (ImageView) findViewById(R.id.nothing);

        plant_happy.setVisibility(View.INVISIBLE);
        plant_sad.setVisibility(View.INVISIBLE);
        flower_happy.setVisibility(View.INVISIBLE);
        flower_sad.setVisibility(View.INVISIBLE);
        nothing.setVisibility(View.INVISIBLE);
        //boolean logout = getIntent().getBooleanExtra("logout",false);


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

        checkchar();
        checkprof();

        toshop = (ImageView) findViewById(R.id.toshop);
        toshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(home_prof.this, activity_shop.class);
                startActivity(intent1);

                mDatabase = null;
                mAuth =null;
            }
        });

        todiary = (ImageView) findViewById(R.id.todiary);
        todiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(home_prof.this, activity_diary.class);
                startActivity(intent2);
                mAuth = null;
                mDatabase = null;
            }
        });

        tomypage = (ImageView) findViewById(R.id.tomypage);
        tomypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(home_prof.this, activity_mypage.class);
                startActivity(intent3);
            }
        });

        profileBtn = (Button) findViewById(R.id.button);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_prof.this, profileEdit.class);
                startActivity(intent);
            }
        });

    }

    public void checkprof(){
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference keyRef = databaseReference.child("User").child("Moisture_degree");
        keyRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // "moisture" 노드가 존재함
                    int moisture_degree = (int)dataSnapshot.getValue(Integer.class);
                    String str_mois = Integer.toString(moisture_degree);
                    water.setText(str_mois+ "%");
                    String good = "좋아용";
                    String bad = "목말라용";
                    String tomuch ="배불러용";

                    Intent intent_pop = new Intent(home_prof.this, home_prof.class);
                    intent_pop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);


                    if(moisture_degree> 40 && moisture_degree<= 70){
                        Log.v("test",moisture_degree+" 높아용");
                        if(character.equals("1")){
                            plant_happy.bringToFront();
                            plant_happy.setVisibility(View.VISIBLE);
                            plant_sad.setVisibility(View.INVISIBLE);
                        }else if(character.equals("2")){
                            flower_happy.bringToFront();
                            flower_happy.setVisibility(View.VISIBLE);
                            flower_sad.setVisibility(View.INVISIBLE);
                        }else if(character.equals("0")){
                            nothing.setVisibility(View.VISIBLE);
                        }
                        state.setText(good);
                        status = 0;
                    }
                    else if(moisture_degree>70){
                        if(character.equals("1")){
                            plant_sad.bringToFront();
                            plant_sad.setVisibility(View.VISIBLE);
                            plant_happy.setVisibility(View.INVISIBLE);
                        }else if(character.equals("2")){
                            flower_sad.bringToFront();
                            flower_sad.setVisibility(View.VISIBLE);
                            flower_happy.setVisibility(View.INVISIBLE);
                        }else if(character.equals("0")){
                            nothing.setVisibility(View.VISIBLE);
                        }
                        state.setText(tomuch);
                        if(status==0){
                            createNotificationChannel(DEFAULT,"default channel", NotificationManager.IMPORTANCE_HIGH);
                            createNotification(DEFAULT,1,"그린맘마",("ㅇ...이제그만.. 나 배불러.."),intent_pop);
                            status = 1;
                        }
                    }
                    else{
                        Log.v("test",moisture_degree + "낮아용");
                        if(character.equals("1")){
                            plant_sad.bringToFront();
                            plant_sad.setVisibility(View.VISIBLE);
                            plant_happy.setVisibility(View.INVISIBLE);
                        }else if(character.equals("2")){
                            flower_sad.bringToFront();
                            flower_sad.setVisibility(View.VISIBLE);
                            flower_happy.setVisibility(View.INVISIBLE);
                        }else if(character.equals("0")){
                            nothing.setVisibility(View.VISIBLE);
                        }
                        state.setText(bad);
                        if(status==0){
                            createNotificationChannel(DEFAULT,"default channel", NotificationManager.IMPORTANCE_HIGH);
                            createNotification(DEFAULT,1,"그린맘마",("으악.. 배고파요....."),intent_pop);
                            status = 1;
                        }
                    }


                } else {
                    Log.v("test","moisture_degree 값 없음 ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }
    public void checkchar(){
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference charRef = databaseReference.child("table").child(uid).child("char").child("char_num");
        charRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int character_num = (int)dataSnapshot.getValue(Integer.class);
                    char_num = Integer.toString(character_num);
                    if(char_num.equals("1")||char_num.equals("2")){
                        character = "1";  //plant
                    }  else if(char_num.equals("3")||char_num.equals("4")){
                        character = "2";  //flower
                    } else{
                        character = "0";
                    }

                } else {
                    Log.v("test","character 값 없음 ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }
    private void createNotificationChannel(String channelId, String channelName, int importance){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,channelName,importance));
        }
    }
    private void createNotification(String channelId,int id, String title, String text,Intent intent){

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(home_prof.this,channelId)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.plant_sad)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id,builder.build());
    }
    private void destroyNotification(int id){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

}