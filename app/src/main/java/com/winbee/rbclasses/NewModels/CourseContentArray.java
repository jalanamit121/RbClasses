package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CourseContentArray implements Serializable {

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
    @SerializedName("Is_New_Batch")
    @Expose
    private Integer is_New_Batch;
    @SerializedName("Is_Any_Class_live")
    @Expose
    private Boolean is_Any_Class_live;
    @SerializedName("Is_Course_Closed_notification")
    @Expose
    private Boolean is_Course_Closed_notification;
    @SerializedName("Show_Course")
    @Expose
    private Boolean show_Course;
    @SerializedName("Course_Closed_details")
    @Expose
    private Course_Closed_details course_Closed_details;
    @SerializedName("Notification")
    @Expose
    private Notification notification;
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

    public Integer getIs_New_Batch() {
        return is_New_Batch;
    }

    public void setIs_New_Batch(Integer is_New_Batch) {
        this.is_New_Batch = is_New_Batch;
    }

    public Boolean getIs_Any_Class_live() {
        return is_Any_Class_live;
    }

    public void setIs_Any_Class_live(Boolean is_Any_Class_live) {
        this.is_Any_Class_live = is_Any_Class_live;
    }

    public Boolean getIs_Course_Closed_notification() {
        return is_Course_Closed_notification;
    }

    public void setIs_Course_Closed_notification(Boolean is_Course_Closed_notification) {
        this.is_Course_Closed_notification = is_Course_Closed_notification;
    }

    public Boolean getShow_Course() {
        return show_Course;
    }

    public void setShow_Course(Boolean show_Course) {
        this.show_Course = show_Course;
    }

    public Course_Closed_details getCourse_Closed_details() {
        return course_Closed_details;
    }

    public void setCourse_Closed_details(Course_Closed_details course_Closed_details) {
        this.course_Closed_details = course_Closed_details;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
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