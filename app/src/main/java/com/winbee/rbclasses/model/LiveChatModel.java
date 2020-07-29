package com.winbee.rbclasses.model;

public class LiveChatModel {
    String Email, Name, Password, FireBaseMobile, userId, message, docId ,time;

    public LiveChatModel() {
    }

    public LiveChatModel(String email, String name, String password, String fireBaseMobile, String userId, String message, String docId, String time) {
        Email = email;
        Name = name;
        Password = password;
        FireBaseMobile = fireBaseMobile;
        this.userId = userId;
        this.message = message;
        this.docId = docId;
        this.time = time;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFireBaseMobile() {
        return FireBaseMobile;
    }

    public void setFireBaseMobile(String fireBaseMobile) {
        FireBaseMobile = fireBaseMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LiveChatModel{" +
                "Email='" + Email + '\'' +
                ", Name='" + Name + '\'' +
                ", Password='" + Password + '\'' +
                ", FireBaseMobile='" + FireBaseMobile + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", docId='" + docId + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}