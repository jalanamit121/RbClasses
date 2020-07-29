package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefCode implements Serializable {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Role_Encode")
    @Expose
    private String role_Encode;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("LoginStatus")
    @Expose
    private Boolean loginStatus;
    @SerializedName("MessageFailure")
    @Expose
    private Boolean messageFailure;
    @SerializedName("CurrentLoginStatus")
    @Expose
    private String currentLoginStatus;
    @SerializedName("Org_Code")
    @Expose
    private String org_Code;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("ref_code")
    @Expose
    private String ref_code;
    @SerializedName("Cred")
    @Expose
    private String cred;



    public RefCode(String username, String name, String email, String role_Encode, String userId, String ref_code, String password) {
        this.username=username;
        this.name=name;
        this.email=email;
        this.role_Encode=role_Encode;
        this.userId=userId;
        this.ref_code=ref_code;
        this.password=password;
    }

    public RefCode() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole_Encode() {
        return role_Encode;
    }

    public void setRole_Encode(String role_Encode) {
        this.role_Encode = role_Encode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Boolean getMessageFailure() {
        return messageFailure;
    }

    public void setMessageFailure(Boolean messageFailure) {
        this.messageFailure = messageFailure;
    }

    public String getCurrentLoginStatus() {
        return currentLoginStatus;
    }

    public void setCurrentLoginStatus(String currentLoginStatus) {
        this.currentLoginStatus = currentLoginStatus;
    }

    public String getOrg_Code() {
        return org_Code;
    }

    public void setOrg_Code(String org_Code) {
        this.org_Code = org_Code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRef_code() {
        return ref_code;
    }

    public void setRef_code(String ref_code) {
        this.ref_code = ref_code;
    }

    public String getCred() {
        return cred;
    }

    public void setCred(String cred) {
        this.cred = cred;
    }

}