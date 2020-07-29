package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewDoubtQuestion implements Serializable {

@SerializedName("response")
@Expose
private String response;
@SerializedName("title")
@Expose
private String title;
@SerializedName("question")
@Expose
private String question;
@SerializedName("DateTime")
@Expose
private String dateTime;
@SerializedName("role")
@Expose
private String role;
@SerializedName("userId")
@Expose
private String userId;

public String getResponse() {
return response;
}

public void setResponse(String response) {
this.response = response;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getQuestion() {
return question;
}

public void setQuestion(String question) {
this.question = question;
}

public String getDateTime() {
return dateTime;
}

public void setDateTime(String dateTime) {
this.dateTime = dateTime;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

}