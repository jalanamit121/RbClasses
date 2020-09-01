package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveMessageArray implements Serializable {

    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("DATE")
    @Expose
    private String dATE;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDATE() {
        return dATE;
    }

    public void setDATE(String dATE) {
        this.dATE = dATE;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
