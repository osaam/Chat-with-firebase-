package com.osamaelsh3rawy.chat.data.model;

public class MessageData {
    private String sender;
    private  String reciver;
    private  String message;

    public MessageData() {
    }

    public MessageData(String sender, String reciver, String message) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
