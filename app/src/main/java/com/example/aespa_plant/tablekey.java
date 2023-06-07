package com.example.aespa_plant;

public class tablekey {
    public String kit_num;
    public String cname;


    public String getKit_num(){
        return kit_num;
    }
    public void setkit_num(String kit_num){
        this.kit_num = kit_num;
    }

    public String getCname(){
        return cname;
    }
    public void setcname(String cname){
        this.cname = cname;
    }

    public tablekey(String kit_num, String cname){
        this.kit_num = kit_num;
        this.cname = cname;
    }
}
