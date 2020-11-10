package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InstructionsModel implements Serializable {

@SerializedName("update_user_status")
@Expose
private String update_user_status;

public String getUpdate_user_status() {
return update_user_status;
}

public void setUpdate_user_status(String update_user_status) {
this.update_user_status = update_user_status;
}

}