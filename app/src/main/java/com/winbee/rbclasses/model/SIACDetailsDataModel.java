package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIACDetailsDataModel {
    @SerializedName("BucketID")
    @Expose
    private String bucketID;
    @SerializedName("PaperID")
    @Expose
    private String paperID;
    @SerializedName("PaperName")
    @Expose
    private String paperName;
    @SerializedName("PaperSection_Encode")
    @Expose
    private String paperSection_Encode;
    @SerializedName("IsNegativeMarking_encode")
    @Expose
    private String isNegativeMarking_encode;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("IsOpen")
    @Expose
    private String isOpen;
    @SerializedName("OpenDate")
    @Expose
    private String openDate;
    @SerializedName("IsAttempted")
    @Expose
    private Boolean isAttempted;
    @SerializedName("Is_Live")
    @Expose
    private Boolean is_Live;
    @SerializedName("Total_Number_of_question")
    @Expose
    private Integer total_Number_of_question;
    @SerializedName("Is_closed_Notification_On")
    @Expose
    private Boolean is_closed_Notification_On;
    @SerializedName("Test_Closed_date")
    @Expose
    private String test_Closed_date;
    @SerializedName("Test_Closed_Message")
    @Expose
    private String test_Closed_Message;
    @SerializedName("IsNegativeMarking_decode")
    @Expose
    private String isNegativeMarking_decode;
    @SerializedName("IsPremium_encode")
    @Expose
    private String isPremium_encode;
    @SerializedName("IsPremium_decode")
    @Expose
    private String isPremium_decode;
    @SerializedName("Description")
    @Expose
    private String description;


    public SIACDetailsDataModel(String bucketID, String paperID, String paperName, String paperSection_Encode, String isNegativeMarking_encode, String time, String isOpen, String openDate, Boolean isAttempted, Boolean is_Live, Integer total_Number_of_question, Boolean is_closed_Notification_On, String test_Closed_date, String test_Closed_Message, String isNegativeMarking_decode, String isPremium_encode, String isPremium_decode, String description) {
        this.bucketID = bucketID;
        this.paperID = paperID;
        this.paperName = paperName;
        this.paperSection_Encode = paperSection_Encode;
        this.isNegativeMarking_encode = isNegativeMarking_encode;
        this.time = time;
        this.isOpen = isOpen;
        this.openDate = openDate;
        this.isAttempted = isAttempted;
        this.is_Live = is_Live;
        this.total_Number_of_question = total_Number_of_question;
        this.is_closed_Notification_On = is_closed_Notification_On;
        this.test_Closed_date = test_Closed_date;
        this.test_Closed_Message = test_Closed_Message;
        this.isNegativeMarking_decode = isNegativeMarking_decode;
        this.isPremium_encode = isPremium_encode;
        this.isPremium_decode = isPremium_decode;
        this.description = description;
    }

    public String getBucketID() {
        return bucketID;
    }

    public void setBucketID(String bucketID) {
        this.bucketID = bucketID;
    }

    public String getPaperID() {
        return paperID;
    }

    public void setPaperID(String paperID) {
        this.paperID = paperID;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperSection_Encode() {
        return paperSection_Encode;
    }

    public void setPaperSection_Encode(String paperSection_Encode) {
        this.paperSection_Encode = paperSection_Encode;
    }

    public String getIsNegativeMarking_encode() {
        return isNegativeMarking_encode;
    }

    public void setIsNegativeMarking_encode(String isNegativeMarking_encode) {
        this.isNegativeMarking_encode = isNegativeMarking_encode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public Boolean getIsAttempted() {
        return isAttempted;
    }

    public void setIsAttempted(Boolean isAttempted) {
        this.isAttempted = isAttempted;
    }

    public Boolean getIs_Live() {
        return is_Live;
    }

    public void setIs_Live(Boolean is_Live) {
        this.is_Live = is_Live;
    }

    public Integer getTotal_Number_of_question() {
        return total_Number_of_question;
    }

    public void setTotal_Number_of_question(Integer total_Number_of_question) {
        this.total_Number_of_question = total_Number_of_question;
    }

    public Boolean getIs_closed_Notification_On() {
        return is_closed_Notification_On;
    }

    public void setIs_closed_Notification_On(Boolean is_closed_Notification_On) {
        this.is_closed_Notification_On = is_closed_Notification_On;
    }

    public String getTest_Closed_date() {
        return test_Closed_date;
    }

    public void setTest_Closed_date(String test_Closed_date) {
        this.test_Closed_date = test_Closed_date;
    }

    public String getTest_Closed_Message() {
        return test_Closed_Message;
    }

    public void setTest_Closed_Message(String test_Closed_Message) {
        this.test_Closed_Message = test_Closed_Message;
    }

    public String getIsNegativeMarking_decode() {
        return isNegativeMarking_decode;
    }

    public void setIsNegativeMarking_decode(String isNegativeMarking_decode) {
        this.isNegativeMarking_decode = isNegativeMarking_decode;
    }

    public String getIsPremium_encode() {
        return isPremium_encode;
    }

    public void setIsPremium_encode(String isPremium_encode) {
        this.isPremium_encode = isPremium_encode;
    }

    public String getIsPremium_decode() {
        return isPremium_decode;
    }

    public void setIsPremium_decode(String isPremium_decode) {
        this.isPremium_decode = isPremium_decode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}