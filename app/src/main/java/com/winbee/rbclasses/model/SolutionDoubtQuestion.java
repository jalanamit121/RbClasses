package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SolutionDoubtQuestion  implements Serializable {

  @SerializedName("response")
  @Expose
  private String response;
  @SerializedName("filename")
  @Expose
  private String filename;
  @SerializedName("answer")
  @Expose
  private String answer;
  @SerializedName("DateTime")
  @Expose
  private String dateTime;
  @SerializedName("role")
  @Expose
  private String role;
  @SerializedName("userid")
  @Expose
  private String userid;

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
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

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

}