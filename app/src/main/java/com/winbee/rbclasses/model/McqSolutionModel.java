package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class McqSolutionModel implements Serializable {

    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Uservalue")
    @Expose
    private String uservalue;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("message")
    @Expose
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUservalue() {
        return uservalue;
    }

    public void setUservalue(String uservalue) {
        this.uservalue = uservalue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}