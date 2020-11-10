package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestTopRanker implements Serializable {

@SerializedName("PaperID")
@Expose
private String paperID;
@SerializedName("UserID")
@Expose
private String userID;
@SerializedName("Is_User_in_top")
@Expose
private Boolean is_User_in_top;
@SerializedName("Data")
@Expose
private TestTopRankerArray[] Data;
@SerializedName("MyData")
@Expose
private TestMyRank[] myData ;


    public Boolean getIs_User_in_top() {
        return is_User_in_top;
    }

    public void setIs_User_in_top(Boolean is_User_in_top) {
        this.is_User_in_top = is_User_in_top;
    }

    public String getPaperID() {
return paperID;
}

public void setPaperID(String paperID) {
this.paperID = paperID;
}

public String getUserID() {
return userID;
}

public void setUserID(String userID) {
this.userID = userID;
}

public TestTopRankerArray[] getData() {
return Data;
}

public TestMyRank[] getMyData() {
return myData;
}

}