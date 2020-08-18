package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResetPassword implements Serializable {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("new_password")
    @Expose
    private String new_password;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}