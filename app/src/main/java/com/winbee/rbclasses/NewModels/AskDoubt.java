package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AskDoubt implements Serializable {

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
private AskDoubtArray[] Data;

    public AskDoubt(Boolean error, String error_Message, String error_Code, AskDoubtArray[] data) {
        this.error = error;
        this.error_Message = error_Message;
        this.error_Code = error_Code;
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

public String getError_Code() {
return error_Code;
}

public void setError_Code(String error_Code) {
this.error_Code = error_Code;
}

    public AskDoubtArray[] getData(){
        return Data;
    }
}