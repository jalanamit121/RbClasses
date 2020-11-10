package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestSubscription implements Serializable {

@SerializedName("Success")
@Expose
private Boolean success;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Data")
@Expose
private TestSubscriptionArray[] data;

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

public TestSubscriptionArray[] getData() {
return data;
}


}