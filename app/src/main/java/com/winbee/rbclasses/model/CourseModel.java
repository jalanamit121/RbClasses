package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CourseModel implements Serializable {
    @SerializedName("Bucket_ID")
    @Expose
    private String bucket_ID;
    @SerializedName("Bucket_Name")
    @Expose
    private String bucket_Name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("display_price")
    @Expose
    private String display_price;
    @SerializedName("discount_price")
    @Expose
    private String discount_price;
    @SerializedName("Total_Video")
    @Expose
    private Integer total_Video;
    @SerializedName("Total_Document")
    @Expose
    private Integer total_Document;
    @SerializedName("Child_Link")
    @Expose
    private String child_Link;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Bucket_Image")
    @Expose
    private String bucket_Image;
    @SerializedName("Bucket_Cover_Image")
    @Expose
    private String bucket_Cover_Image;
    @SerializedName("Paid")
    @Expose
    private Integer paid;

    public String getBucket_ID() {
        return bucket_ID;
    }

    public void setBucket_ID(String bucket_ID) {
        this.bucket_ID = bucket_ID;
    }

    public String getBucket_Name() {
        return bucket_Name;
    }

    public void setBucket_Name(String bucket_Name) {
        this.bucket_Name = bucket_Name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDisplay_price() {
        return display_price;
    }

    public void setDisplay_price(String display_price) {
        this.display_price = display_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public Integer getTotal_Video() {
        return total_Video;
    }

    public void setTotal_Video(Integer total_Video) {
        this.total_Video = total_Video;
    }

    public Integer getTotal_Document() {
        return total_Document;
    }

    public void setTotal_Document(Integer total_Document) {
        this.total_Document = total_Document;
    }

    public String getChild_Link() {
        return child_Link;
    }

    public void setChild_Link(String child_Link) {
        this.child_Link = child_Link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBucket_Image() {
        return bucket_Image;
    }

    public void setBucket_Image(String bucket_Image) {
        this.bucket_Image = bucket_Image;
    }

    public String getBucket_Cover_Image() {
        return bucket_Cover_Image;
    }

    public void setBucket_Cover_Image(String bucket_Cover_Image) {
        this.bucket_Cover_Image = bucket_Cover_Image;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

}