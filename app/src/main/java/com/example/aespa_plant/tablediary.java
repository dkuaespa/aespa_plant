package com.example.aespa_plant;

public class tablediary {
    public String imgString;
    public String img_key;
    public String content;

    public String getImgString(){
        return imgString;
    }
    public void setImgString(String imgString){
        this.imgString = imgString;
    }



    public String getImg_key(){
        return img_key;
    }
    public void setImg_key(String img_key){
        this.img_key = img_key;
    }

    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content= content;
    }



    public tablediary(String imgString, String img_key, String content){
        this.imgString = imgString;
        this.img_key = img_key;
        this.content = content;
    }
}
