package com.example.chatgpt;

public class Messsege {
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";
    String message;
    String sentby;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSentby() {
        return sentby;
    }
    public void setSentby(String sentby) {
        this.sentby = sentby;
    }
    public Messsege(String message, String sentby) {
        this.message = message;
        this.sentby = sentby;
    }
}
