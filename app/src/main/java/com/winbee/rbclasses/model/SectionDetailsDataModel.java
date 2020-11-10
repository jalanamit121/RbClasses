package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionDetailsDataModel {
    @SerializedName("CoachingID")
    @Expose
    private String coachingID;
    @SerializedName("BucketID")
    @Expose
    private String bucketID;
    @SerializedName("BucketName")
    @Expose
    private String bucketName;
    @SerializedName("BucketInfo")
    @Expose
    private String bucketInfo;
    @SerializedName("LogData")
    @Expose
    private String logData;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("TotalTest")
    @Expose
    private String totalTest;
    @SerializedName("IsPremium")
    @Expose
    private String isPremium;
    @SerializedName("Decription")
    @Expose
    private String decription;
    @SerializedName("IsPaid")
    @Expose
    private Integer isPaid;
    @SerializedName("Is_Section_Live")
    @Expose
    private Integer is_Section_Live;
    @SerializedName("ItemAttachment")
    @Expose
    private String itemAttachment;
    @SerializedName("IsCommingSoon")
    @Expose
    private String isCommingSoon;

    public SectionDetailsDataModel(String coachingID, String bucketID, String bucketName, String bucketInfo, String logData, String status, String totalTest, String isPremium, String decription, Integer isPaid, Integer is_Section_Live, String itemAttachment, String isCommingSoon) {
        this.coachingID = coachingID;
        this.bucketID = bucketID;
        this.bucketName = bucketName;
        this.bucketInfo = bucketInfo;
        this.logData = logData;
        this.status = status;
        this.totalTest = totalTest;
        this.isPremium = isPremium;
        this.decription = decription;
        this.isPaid = isPaid;
        this.is_Section_Live = is_Section_Live;
        this.itemAttachment = itemAttachment;
        this.isCommingSoon = isCommingSoon;
    }

    public String getCoachingID() {
        return coachingID;
    }

    public void setCoachingID(String coachingID) {
        this.coachingID = coachingID;
    }

    public String getBucketID() {
        return bucketID;
    }

    public void setBucketID(String bucketID) {
        this.bucketID = bucketID;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketInfo() {
        return bucketInfo;
    }

    public void setBucketInfo(String bucketInfo) {
        this.bucketInfo = bucketInfo;
    }

    public String getLogData() {
        return logData;
    }

    public void setLogData(String logData) {
        this.logData = logData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalTest() {
        return totalTest;
    }

    public void setTotalTest(String totalTest) {
        this.totalTest = totalTest;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getIs_Section_Live() {
        return is_Section_Live;
    }

    public void setIs_Section_Live(Integer is_Section_Live) {
        this.is_Section_Live = is_Section_Live;
    }

    public String getItemAttachment() {
        return itemAttachment;
    }

    public void setItemAttachment(String itemAttachment) {
        this.itemAttachment = itemAttachment;
    }

    public String getIsCommingSoon() {
        return isCommingSoon;
    }

    public void setIsCommingSoon(String isCommingSoon) {
        this.isCommingSoon = isCommingSoon;
    }

}