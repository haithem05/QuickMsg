package com.example.quickmsg;

public class UserData {
    public String user_id;
    public String user_name;
    public String user_picture;
    public UserData(){}

    public UserData(String user_id) {
        this.user_id = user_id;
    }

    public UserData(String user_name, String user_picture) {
        this.user_name = user_name;
        this.user_picture = user_picture;
    }
    public UserData(String user_id,String user_name, String user_picture) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_picture = user_picture;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }
}
