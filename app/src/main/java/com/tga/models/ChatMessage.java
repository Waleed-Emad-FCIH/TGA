package com.tga.models;

public class ChatMessage {
    private String messageText,messageUser;
    String messageTime;
    private  String image;


    public ChatMessage() {
    }

    public ChatMessage(String messageText, String messageUser) {

        this.messageText = messageText;
        this.messageUser = messageUser;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}


