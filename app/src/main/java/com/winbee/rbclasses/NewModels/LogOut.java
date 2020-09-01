package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LogOut implements Serializable {

@SerializedName("LoginStatus")
@Expose
private Boolean loginStatus;
@SerializedName("MessageFailure")
@Expose
private String messageFailure;
@SerializedName("Error")
@Expose
private Boolean error;
@SerializedName("Error_Message")
@Expose
private String error_Message;
@SerializedName("Error_Code")
@Expose
private String error_Code;

public Boolean getLoginStatus() {
return loginStatus;
}

public void setLoginStatus(Boolean loginStatus) {
this.loginStatus = loginStatus;
}

public String getMessageFailure() {
return messageFailure;
}

public void setMessageFailure(String messageFailure) {
this.messageFailure = messageFailure;
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

}