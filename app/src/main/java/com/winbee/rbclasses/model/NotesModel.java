package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotesModel implements Serializable {

    @SerializedName("Notes_type")
    @Expose
    private String notes_type;
    @SerializedName("Notes_data")
    @Expose
    private String notes_data;
    @SerializedName("Notes_Topic")
    @Expose
    private String notes_Topic;
    @SerializedName("Is_Premium")
    @Expose
    private Boolean is_Premium;
    @SerializedName("log_details")
    @Expose
    private String log_details;

    public String getNotes_type() {
        return notes_type;
    }

    public void setNotes_type(String notes_type) {
        this.notes_type = notes_type;
    }

    public String getNotes_data() {
        return notes_data;
    }

    public void setNotes_data(String notes_data) {
        this.notes_data = notes_data;
    }

    public String getNotes_Topic() {
        return notes_Topic;
    }

    public void setNotes_Topic(String notes_Topic) {
        this.notes_Topic = notes_Topic;
    }

    public Boolean getIs_Premium() {
        return is_Premium;
    }

    public void setIs_Premium(Boolean is_Premium) {
        this.is_Premium = is_Premium;
    }

    public String getLog_details() {
        return log_details;
    }

    public void setLog_details(String log_details) {
        this.log_details = log_details;
    }

}