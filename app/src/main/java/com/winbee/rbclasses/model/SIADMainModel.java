package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIADMainModel {
    @Expose
    @SerializedName("Success")
    private String Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    private SIADDataModel Data;

    public SIADMainModel(String success, String message, SIADDataModel data) {
        Success = success;
        Message = message;
        Data = data;
    }

    public String getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public SIADDataModel getData() {
        return Data;
    }
}
