package com.hyphenate.chatuidemo;

public class Newpackets {
    private int type;
    private String id;
    private String imageUrl;
    private String status;
    private String positionNow;
    private String position;
    private String userMobile;
    private String courierMobile;
    private String userName;

    public Newpackets(int type, String id, String status, String imageUrl, String position, String positionNow, String userMobile, String userName, String courierMobile){
        this.type=type;
        this.id = id;
        this.status=status;
        this.imageUrl=imageUrl;
        this.positionNow=positionNow;
        this.position=position;
        this.userMobile=userMobile;
        this.courierMobile=courierMobile;
        this.userName=userName;
    }
    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type=type;
    }


    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPosition() {
        return position;
    }

    public String getPositionNow() {
        return positionNow;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getCourierMobile() {
        return courierMobile;
    }

    public String getUserName() {
        return userName;
    }



    public void setId(String id){
        this.id=id;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setPositionNow(String positionNow){
        this.positionNow=positionNow;
    }
    public void setPosition(String position){
        this.position=position;
    }
    public void setUserMobile(String userMobile){
        this.userMobile=userMobile;
    }
    public void setCourierMobile(String courierMobile){
        this.courierMobile=courierMobile;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }


}
