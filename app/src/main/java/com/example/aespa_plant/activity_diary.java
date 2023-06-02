package com.example.aespa_plant;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Base64;
import java.util.HashMap;

public class activity_diary extends AppCompatActivity
{

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = database.getReference();

    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    private static final int REQUEST_CODE = 1;
    public String str = null;
    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn, photo_Btn;
    public TextView diaryTextView, textView2;
    public ImageView photoView;
    public EditText contextEditText;
    private ImageView back;


    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    //이미지 bitmap 스트링
    String imgName = "my_image.jpg";
    String img_key = "";
    Bitmap bitmap;
    String imgString;
    //일기장 내용
    String content;
    private Object tablediary;
    String uid = user.getUid();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        save_Btn = findViewById(R.id.save_Btn);
        photo_Btn = findViewById(R.id.photo_Btn);
        del_Btn = findViewById(R.id.del_Btn);
        cha_Btn = findViewById(R.id.cha_Btn);
        textView2 = findViewById(R.id.textView2);
        photoView = findViewById(R.id.photoView);
        contextEditText = findViewById(R.id.contextEditText);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {// 날짜 선택 시 호출되는 코드
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                photo_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                photoView.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                contextEditText.setText("");

                // 선택한 날짜 정보 저장
                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;
                img_key = year + "-" + (month + 1) + "-" + dayOfMonth;
                Log.e("test", img_key+"날짜 클릭");

                //checkday 로 선택 날짜 정보 이동
                checkDay(img_key);
                //날짜있으면 fetch 로 가서 값 가져오기
                //날짜없으면 update 로 가서 글 쓰기


            }

        });

        back = (ImageView) findViewById(R.id.toshop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_diary.this, home_prof.class);
                startActivity(intent);
            }
        });

    }


    // 사진 회전
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    // 이미지 선택 후 결과 처리
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            // 사진 추가 버튼 클릭 시 갤러리에서 사진을 선택하고 저장하는 코드

            // 선택한 이미지의 Uri 가져오기
            Uri selectedImageUri = data.getData();

            try {
                // ContentResolver를 통해 이미지 읽어오기
                ContentResolver resolver = getContentResolver();
                InputStream inputStream = resolver.openInputStream(selectedImageUri);

                // BitmapFactory를 통해 이미지를 Bitmap으로 디코딩
                bitmap = BitmapFactory.decodeStream(inputStream);

                // 이미지 회전
                //bitmap = rotateImage(bitmap, 90);

                // 이미지뷰에 선택한 이미지 표시
                photoView.setImageBitmap(bitmap);

                // InputStream 닫기
                inputStream.close();

                // 이미지 저장
                int year = selectedYear;
                int month = selectedMonth;
                int dayOfMonth = selectedDay;

                saveBitmapToJpeg(bitmap, year, month, dayOfMonth);

                Toast.makeText(getApplicationContext(), "사진 불러오기 및 저장 성공", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "사진 불러오기 및 저장 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveBitmapToJpeg(Bitmap imgBitmap, int year, int month, int day) {
        if(imgBitmap != null){
            try {
                //파이어베이스에 bitmap을 string으로 변환하여 img_String에 저장
                imgString = bitmapToString(imgBitmap);

                Toast.makeText(getApplicationContext(), "이미지 파이어베이스에 저장 성공", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "이미지 파이어베이스에 저장 실패", Toast.LENGTH_SHORT).show();
            }
        }

    }
    // 일기 저장
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    public void saveDiary(String img_key)
    {

        saveBitmapToJpeg(bitmap, selectedYear, selectedMonth, selectedDay);
        if(bitmap == null){
            imgString="0";
        }
        content = contextEditText.getText().toString();

        // 파이어베이스에 저장(img_key, imgString, content)
        tablediary(imgString,img_key,content);

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkDay(String img) {
        databaseReference.child("table").child(uid).child("diary").child(img).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.e("test", "날짜 존재");
                    String cont = snapshot.child("content").getValue(String.class);
                    String img_k = snapshot.child("img_key").getValue(String.class);
                    String imgs = snapshot.child("imgString").getValue(String.class);
                    //데이터값 있으면 fetch 로 텍스트 창 변경 후 데이터 표시
                    fetch(cont,img_k,imgs);
                    Log.e("test", cont+"컨텐츠 확인");
                    Log.e("test", img_k+"날짜(키) 확인");
                    Log.e("test", imgs+"이미지 확인");
                }
                else{
                    //데이터값 없으면 사진/글 입력받기
                    update();
                    Log.e("test",  "날짜 존재하지 않음");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fetch(String cont,String img_k,String imgs) {
        //파이어베이스에서 imgString,content 가져오기
        //string을 bitmap으로 재변환
        try {
            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(cont);

            save_Btn.setVisibility(View.INVISIBLE);
            photo_Btn.setVisibility(View.INVISIBLE);

            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            if(imgs.length()>1){
                Log.e("test",  "사진이 존재함");
                Bitmap bitmap = stringToBitmap(imgs);
                photoView.setVisibility(View.VISIBLE);
                photoView.setImageBitmap(bitmap);

            }
            else{
                Log.e("test",  "사진이 존재하지 않음");
                photoView.setImageBitmap(null);
                photoView.setVisibility(View.INVISIBLE);

            }
            // 수정 버튼 클릭시
            cha_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    photoView.setVisibility(View.INVISIBLE);
                    contextEditText.setText(content);
                    save_Btn.setVisibility(View.VISIBLE);
                    photo_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);

                    // 사진 추가 버튼 클릭 이벤트 처리
                    photo_Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 사진 추가 버튼 클릭 시 갤러리에서 사진을 선택하고 저장하는 코드

                            // 갤러리 호출을 위한 인텐트 생성
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                            // 갤러리 호출 및 결과 처리
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    });

                    // 저장 버튼 클릭 이벤트 처리
                    save_Btn.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            saveDiary(img_key);
                            str = contextEditText.getText().toString();
                            textView2.setText(str);
                            save_Btn.setVisibility(View.INVISIBLE);
                            photo_Btn.setVisibility(View.INVISIBLE);
                            cha_Btn.setVisibility(View.VISIBLE);
                            del_Btn.setVisibility(View.VISIBLE);
                            contextEditText.setVisibility(View.INVISIBLE);
                            textView2.setVisibility(View.VISIBLE);
                            photoView.setVisibility(View.VISIBLE);

                        }
                    });

                    // textView2.setText(contextEditText.getText());
                }

            });

            // 삭제 버튼 클릭시
            del_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView2.setVisibility(View.INVISIBLE);
                    photoView.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    photo_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(img_key);
                    Toast.makeText(getApplicationContext(), "일기 삭제됨", Toast.LENGTH_SHORT).show();
                }
            });

            if (textView2.getText() == null) {
                textView2.setVisibility(View.INVISIBLE);
                photoView.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                photo_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(){
        //날짜 클릭 시 데이터가 없으면 글 쓰는 기능
        // 사진 추가 버튼 클릭 이벤트 처리
        photo_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사진 추가 버튼 클릭 시 갤러리에서 사진을 선택하고 저장하는 코드

                // 갤러리 호출을 위한 인텐트 생성
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                // 갤러리 호출 및 결과 처리
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // 저장 버튼 클릭 이벤트 처리
        save_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveDiary(img_key);
                str = contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                photo_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);
                photoView.setVisibility(View.VISIBLE);


            }
        });
    }

    // 파이어베이스에서 이미지 파일이 존재하는지 확인
    private boolean isImageExist(int year, int month, int day) {
        boolean go = false;
        //파이어베이스에서 imagetring이 있는지 확인
        //img_key에 해당하는 값이 있는지 확인

        return go;
    }

    // 파이어베이스에서 다이어리 내용이 존재하는지 확인
    private boolean isContentExist(int year, int month, int day) {
        boolean go = false;
        //파이어베이스에서 imagetring이 있는지 확인
        //img_key에 해당하는 값이 있는지 확인

        return go;
    }


    // 일기 삭제
    @SuppressLint("WrongConstant")
    public void removeDiary(String img_key)
    {
        databaseReference.child("table").child(uid).child("diary").child(img_key).setValue(null);
        Log.e("test",  "파이어베이스에서 다이어리 삭제하겠음");

    }


    //bitmap 을  string 형태로 변환하는 메서드 (이렇게 string 으로 변환된 데이터를 mysql 에서 longblob 의 형태로 저장하는식으로 사용가능)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String bitmapToString(Bitmap bitmap){
        String image = "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        image = Base64.getEncoder().encodeToString(byteArray);
        return image;
    }

    //string 을  bitmap 형태로 변환하는 메서드
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap stringToBitmap(String imgString){
        Bitmap bitmap = null;
        byte[] byteArray = Base64.getDecoder().decode(imgString);
        ByteArrayInputStream stream = new ByteArrayInputStream(byteArray);
        bitmap = BitmapFactory.decodeStream(stream);
        return bitmap;



    }
    //파이어베이스 저장
    public void tablediary(String imgString, String img_key,String content){
        tablediary tablediary = new tablediary(imgString,img_key, content);
        databaseReference.child("table").child(uid).child("diary").child(img_key).setValue(tablediary);
    }

}