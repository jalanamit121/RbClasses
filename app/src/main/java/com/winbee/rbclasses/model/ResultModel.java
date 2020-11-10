package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultModel {
    @Expose
    @SerializedName("CoachingID")
    private String CoachingID;
    @Expose
    @SerializedName("PaperID")
    private String PaperID;
    @Expose
    @SerializedName("UserID")
    private String UserID;

    public String getCoachingID() {
        return CoachingID;
    }

    public void setCoachingID(String coachingID) {
        CoachingID = coachingID;
    }

    public String getPaperID() {
        return PaperID;
    }

    public void setPaperID(String paperID) {
        PaperID = paperID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
