package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestMyRank implements Serializable {
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("TotalScore")
    @Expose
    private String totalScore;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Rank")
    @Expose
    private Integer rank;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
