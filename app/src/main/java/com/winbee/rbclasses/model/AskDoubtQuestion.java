package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AskDoubtQuestion implements Serializable {
    @SerializedName("file_name")
    @Expose
    private String file_name;
    @SerializedName("file_name_to_show")
    @Expose
    private String file_name_to_show;
    @SerializedName("file_create_name")
    @Expose
    private String file_create_name;
    @SerializedName("file_likes")
    @Expose
    private String file_likes;
    @SerializedName("file_comments")
    @Expose
    private String file_comments;
    @SerializedName("file_create_date")
    @Expose
    private String file_create_date;
    @SerializedName("file_duration_time")
    @Expose
    private String file_duration_time;
    @SerializedName("file_description")
    @Expose
    private String file_description;
    @SerializedName("DocumentId")
    @Expose
    private String documentId;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_name_to_show() {
        return file_name_to_show;
    }

    public void setFile_name_to_show(String file_name_to_show) {
        this.file_name_to_show = file_name_to_show;
    }

    public String getFile_create_name() {
        return file_create_name;
    }

    public void setFile_create_name(String file_create_name) {
        this.file_create_name = file_create_name;
    }

    public String getFile_likes() {
        return file_likes;
    }

    public void setFile_likes(String file_likes) {
        this.file_likes = file_likes;
    }

    public String getFile_comments() {
        return file_comments;
    }

    public void setFile_comments(String file_comments) {
        this.file_comments = file_comments;
    }

    public String getFile_create_date() {
        return file_create_date;
    }

    public void setFile_create_date(String file_create_date) {
        this.file_create_date = file_create_date;
    }

    public String getFile_duration_time() {
        return file_duration_time;
    }

    public void setFile_duration_time(String file_duration_time) {
        this.file_duration_time = file_duration_time;
    }

    public String getFile_description() {
        return file_description;
    }

    public void setFile_description(String file_description) {
        this.file_description = file_description;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}