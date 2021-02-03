package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RazorPayModel implements Serializable {

@SerializedName("API_Key")
@Expose
private String aPI_Key;
@SerializedName("Secret_Key")
@Expose
private String secret_Key;

public String getAPI_Key() {
return aPI_Key;
}

public void setAPI_Key(String aPI_Key) {
this.aPI_Key = aPI_Key;
}

public String getSecret_Key() {
return secret_Key;
}

public void setSecret_Key(String secret_Key) {
this.secret_Key = secret_Key;
}

}