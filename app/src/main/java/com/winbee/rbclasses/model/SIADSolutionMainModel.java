package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIADSolutionMainModel {
    @Expose
    @SerializedName("Success")
    private String Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    private SIADSolutionDataModel Data;

    public SIADSolutionMainModel(String success, String message, SIADSolutionDataModel data) {
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

    public SIADSolutionDataModel getData() {
        return Data;
    }
}
