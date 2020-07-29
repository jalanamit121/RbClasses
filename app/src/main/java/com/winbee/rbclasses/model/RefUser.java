package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefUser implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("refcode")
    @Expose
    private String refcode;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("registration_id")
    @Expose
    private Object registration_id;
    @SerializedName("OTP")
    @Expose
    private Object oTP;

    public RefUser(String name, String mobile, String email, String password, String referalcode) {
        this.name = name;
        this.mobile = mobile;
        this.email=email;
        this.password=password;
        this.refcode=referalcode;

    }



    public RefUser() {

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
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

    public Object getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(Object registration_id) {
        this.registration_id = registration_id;
    }

    public Object getOTP() {
        return oTP;
    }

    public void setOTP(Object oTP) {
        this.oTP = oTP;
    }

}