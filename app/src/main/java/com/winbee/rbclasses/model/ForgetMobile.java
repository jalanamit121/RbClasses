package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForgetMobile implements Serializable {

@SerializedName("username")
@Expose
private String username;
@SerializedName("otp")
@Expose
private String otp;
@SerializedName("Success")
@Expose
private Boolean success;
@SerializedName("Message")
@Expose
private String message;

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getOtp() {
return otp;
}

public void setOtp(String otp) {
this.otp = otp;
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

}