package com.hyphenate.chatuidemo;

public class Packets  {
    private String id;
    private String imageUrl;
    private String status;
    private String positionNow;
    private String position;
    private String userMobile;
    private String courierMobile;
    private String userName;

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public String getPositionNow() {
        return positionNow;
    }

    public String getPosition() {
        return position;
    }

    public String getCourierMobile() {
        return courierMobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public void setPositionNow(String positionNow){
        this.positionNow=positionNow;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setPosition(String position){
        this.position = position;
    }
    public void setUserMobile(String userMobile){this.userMobile = userMobile;}
    public void setCourierMobile(String courierMobile){this.courierMobile=courierMobile;}
    public void setUserName(String userName){this.userName=userName;}
}
