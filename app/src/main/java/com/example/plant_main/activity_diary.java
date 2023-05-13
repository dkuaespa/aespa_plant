package com.example.plant_main;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class activity_diary extends AppCompatActivity
{
    private static final int REQUEST_CODE = 1;
    public String readDay = null;
    public String str = null;
    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn, photo_Btn;
    public TextView diaryTextView, textView2;
    public ImageView photoView;
    public EditText contextEditText;

    String imgName = "my_image.jpg";

    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

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



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {// 날짜 선택 시 호출되는 코드

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
                checkDay(year, month, dayOfMonth);

                // 선택한 날짜 정보 저장
                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;

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

                        saveDiary(readDay);
                        Bitmap imgBitmap = null;
                        saveBitmapToJpeg(imgBitmap, selectedYear, selectedMonth, selectedDay);
                        str = contextEditText.getText().toString();
                        textView2.setText(str);
                        save_Btn.setVisibility(View.INVISIBLE);
                        photo_Btn.setVisibility(View.INVISIBLE);
                        cha_Btn.setVisibility(View.VISIBLE);
                        del_Btn.setVisibility(View.VISIBLE);
                        contextEditText.setVisibility(View.INVISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        if (isImageExist(selectedYear, selectedMonth, selectedDay)) {
                            photoView.setVisibility(View.VISIBLE);
                        } else {
                            photoView.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                // 기존에 저장된 사진 로드 및 표시
                if (isImageExist(selectedYear, selectedMonth, selectedDay)) {
                    try {
                        // 이미지 파일 경로 설정
                        String imgPath = getCacheDir() + "/" + getImageFileName(selectedYear, selectedMonth, selectedDay);

                        // 이미지 파일 로드
                        Bitmap bm = BitmapFactory.decodeFile(imgPath);

                        // 이미지뷰에 이미지 표시
                        photoView.setImageBitmap(bm);

                        Toast.makeText(getApplicationContext(), "파일 로드 성공", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 이미지가 없는 경우 이미지뷰를 숨김
                    photoView.setImageBitmap(null);
                    photoView.setVisibility(View.VISIBLE);
                }

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

    // 이미지 선택 후 결과 처리리
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
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // 이미지 회전
                bitmap = rotateImage(bitmap, 90);

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


    private void saveBitmapToJpeg(Bitmap imgBitmap, int year, int month, int day) {
        if(imgBitmap != null){
            // 이미지 파일 경로 설정
            String imgFileName = getImageFileName(year, month, day);
            String imgPath = getCacheDir() + "/" + imgFileName;

            try {
                // 파일 생성
                File file = new File(imgPath);
                file.createNewFile();

                // 파일 출력 스트림 생성
                FileOutputStream out = new FileOutputStream(file);

                // 비트맵 이미지를 JPEG 형식으로 압축하여 파일에 저장
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                // 출력 스트림 닫기
                out.close();

                Toast.makeText(getApplicationContext(), "이미지 저장 성공", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "이미지 저장 실패", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void checkDay(int cYear, int cMonth, int cDay)
    {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        FileInputStream fis;

        try
        {
            fis = openFileInput(readDay);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);
            photoView.setVisibility(View.VISIBLE);
            save_Btn.setVisibility(View.INVISIBLE);
            photo_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            cha_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    photoView.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);
                    save_Btn.setVisibility(View.VISIBLE);
                    photo_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    textView2.setText(contextEditText.getText());
                }

            });
            del_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    textView2.setVisibility(View.INVISIBLE);
                    photoView.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    photo_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(readDay);
                    Toast.makeText(getApplicationContext(), "일기 삭제됨", Toast.LENGTH_SHORT).show();
                }
            });
            if (textView2.getText() == null)
            {
                textView2.setVisibility(View.INVISIBLE);
                photoView.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                photo_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // 일기 삭제
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // 일기 저장
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // 이미지 파일이 존재하는지 확인
    private boolean isImageExist(int year, int month, int day) {
        String imgFileName = getImageFileName(year, month, day);
        String imgPath = getCacheDir() + "/" + imgFileName;
        File file = new File(imgPath);
        return file.exists();
    }

    // 이미지 파일 이름 생성
    private String getImageFileName(int year, int month, int day) {
        return year + "-" + (month + 1) + "-" + day + ".jpg";
    }
}