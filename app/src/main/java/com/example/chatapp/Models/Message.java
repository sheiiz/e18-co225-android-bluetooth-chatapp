package com.example.chatapp.Models;

public class Message {
    String message;
    String name;
    String key;

    public Message(){

    }
    public Message(String message, String name, String key){
        this.message = message;
        this.name = name;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
