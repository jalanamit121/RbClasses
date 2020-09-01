package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveChatMessage implements Serializable {

    @SerializedName("Response")
    @Expose
    private Boolean response;
    @SerializedName("ResponseMessage")
    @Expose
    private Object responseMessage;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("LiveClassId")
    @Expose
    private String liveClassId;
    @SerializedName("Messages")
    @Expose
    private String messages;
    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Error_Message")
    @Expose
    private String error_Message;
    @SerializedName("Error_Code")
    @Expose
    private String error_Code;
    @SerializedName("Data")
    @Expose
    private String data;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public Object getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(Object responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLiveClassId() {
        return liveClassId;
    }

    public void setLiveClassId(String liveClassId) {
        this.liveClassId = liveClassId;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getError_Message() {
        return error_Message;
    }

    public void setError_Message(String error_Message) {
        this.error_Message = error_Message;
    }

    public String getError_Code() {
        return error_Code;
    }

    public void setError_Code(String error_Code) {
        this.error_Code = error_Code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}