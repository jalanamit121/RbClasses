package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveMessage  implements Serializable {

@SerializedName("Response")
@Expose
private Boolean response;
@SerializedName("ResponseMessage")
@Expose
private String responseMessage;
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
private LiveMessageArray[] Data;

public Boolean getResponse() {
return response;
}

public void setResponse(Boolean response) {
this.response = response;
}

public String getResponseMessage() {
return responseMessage;
}

public void setResponseMessage(String responseMessage) {
this.responseMessage = responseMessage;
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

    public LiveMessageArray[] getData(){
        return Data;
    }

}