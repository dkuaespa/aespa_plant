package com.example.aespa_plant;


public class tablename {
    public String username; //사용자 닉네임
    public String email;
    public String pw;


    //String calendarr[];
    int img_num; //캐릭터 넘버
    int moisture; //수분 센서값

    //   Drawable image = .getDrawable();
    //   String simage ="";

    public String getUsername(){
        return username;
    }
    public void setUsername(String name){
        this.username = username;
    }


    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPw(){
        return pw;
    }
    public void setPw(String pw){
        this.pw = pw;
    }

    //(윤아가)센서에서  값을 파이어베이스에 보냄 ex) 0x11
    //사용자가 휴대폰 상에서 0x11 ( 코드 값을 입력함 이때 이미 로그인한 상태) 을 입력해 파이어베이스로 보냄(zoo/kit_num)
    // User/kit_num == zoo/kit_num 이 같으면
    // User/Moisture_degree 을 zoo/Moisture_degree 에 저장하긔

    //값을 추가할때 쓰는 함수
    public tablename(String username, String email, String pw){
        this.username = username;
        this.email = email;
        this.pw = pw;
    }
}