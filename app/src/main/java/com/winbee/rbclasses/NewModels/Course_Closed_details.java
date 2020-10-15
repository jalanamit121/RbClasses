package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Course_Closed_details  implements Serializable {

    @SerializedName("Closed_Date_time")
    @Expose
    private String closed_Date_time;
    @SerializedName("Is_Course_Closed")
    @Expose
    private Boolean is_Course_Closed;
    @SerializedName("Is_Course_Closed_purchase_Message")
    @Expose
    private String is_Course_Closed_purchase_Message;
    @SerializedName("Remaining_Time")
    @Expose
    private String remaining_Time;
    @SerializedName("Closed_Course_Message")
    @Expose
    private String closed_Course_Message;
    @SerializedName("Is_timing_show")
    @Expose
    private Boolean is_timing_show;

    public String getClosed_Date_time() {
        return closed_Date_time;
    }

    public void setClosed_Date_time(String closed_Date_time) {
        this.closed_Date_time = closed_Date_time;
    }

    public Boolean getIs_Course_Closed() {
        return is_Course_Closed;
    }

    public void setIs_Course_Closed(Boolean is_Course_Closed) {
        this.is_Course_Closed = is_Course_Closed;
    }

    public String getIs_Course_Closed_purchase_Message() {
        return is_Course_Closed_purchase_Message;
    }

    public void setIs_Course_Closed_purchase_Message(String is_Course_Closed_purchase_Message) {
        this.is_Course_Closed_purchase_Message = is_Course_Closed_purchase_Message;
    }

    public String getRemaining_Time() {
        return remaining_Time;
    }

    public void setRemaining_Time(String remaining_Time) {
        this.remaining_Time = remaining_Time;
    }

    public String getClosed_Course_Message() {
        return closed_Course_Message;
    }

    public void setClosed_Course_Message(String closed_Course_Message) {
        this.closed_Course_Message = closed_Course_Message;
    }

    public Boolean getIs_timing_show() {
        return is_timing_show;
    }

    public void setIs_timing_show(Boolean is_timing_show) {
        this.is_timing_show = is_timing_show;
    }

}