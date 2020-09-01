package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.winbee.rbclasses.model.QuetionDatum;

import java.io.Serializable;

public class DailyUpdate implements Serializable {

@SerializedName("Error")
@Expose
private Boolean error;
@SerializedName("Error_Message")
@Expose
private String error_Message;
@SerializedName("Data")
@Expose
private DailyUpdateArray[] Data;

    public DailyUpdate(Boolean error, String error_Message, DailyUpdateArray[] data) {
        this.error = error;
        this.error_Message = error_Message;
        Data = data;
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

    public DailyUpdateArray[] getData(){
        return Data;
    }

}