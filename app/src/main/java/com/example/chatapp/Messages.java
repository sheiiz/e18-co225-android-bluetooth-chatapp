package com.example.chatapp;

public class Messages {


    private String message;
    private String type;
    private int msgID;
    private int chatID;


    public Messages() {
    }


    public Messages(int msgID, int chatID, String type, String message) {
        this.message = message;
        this.type = type;
        this.chatID  = chatID;
        this.msgID  = msgID;
    }

    public int getMsgID() {
        return msgID;
    }

    public int getChatID() {
        return chatID;
    }
    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMsgID(int msgID) {
        this.msgID = msgID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
