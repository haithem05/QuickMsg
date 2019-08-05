package com.example.quickmsg;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    public String message_text;
    public String from_user;
    public String to_user;
    public String from_user_id;
    public String to_user_id;
    public long message_time;

    public ChatMessage(String message_text, String from_user,String to_user,String from_user_id,String to_user_id) {
        this.message_text = message_text;
        this.from_user = from_user;
        this.to_user = to_user;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        message_time = new Date().getTime();
    }

    public ChatMessage(String message_text, String from_user,String to_user,String from_user_id,String to_user_id,long message_time) {
        this.message_text = message_text;
        this.from_user = from_user;
        this.to_user = to_user;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        this.message_time = message_time;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getfrom_user() {
        return from_user;
    }

    public void setfrom_user(String from_user) {
        this.from_user = from_user;
    }

    public long getMessage_time() {
        return message_time;
    }

    public void setMessage_time(long message_time) {
        this.message_time = message_time;
    }
}
