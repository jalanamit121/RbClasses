package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SolutionQuestion implements Serializable {

    @SerializedName("DATE")
    @Expose
    private String dATE;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("User")
    @Expose
    private String user;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("Duration")
    @Expose
    private String duration;
    @SerializedName("Closed")
    @Expose
    private String closed;

    public String getDATE() {
        return dATE;
    }

    public void setDATE(String dATE) {
        this.dATE = dATE;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

}