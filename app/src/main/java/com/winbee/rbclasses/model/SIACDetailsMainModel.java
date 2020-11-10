package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIACDetailsMainModel {
    @Expose
    @SerializedName("Success")
    private String Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    private SIACDetailsDataModel[] Data;

    public String getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public SIACDetailsDataModel[] getData() {
        return Data;
    }
}
