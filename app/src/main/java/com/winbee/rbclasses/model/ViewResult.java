package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewResult implements Serializable {

@SerializedName("PaperID")
@Expose
private String paperID;
@SerializedName("UserID")
@Expose
private String userID;
@SerializedName("OrgID")
@Expose
private String orgID;
@SerializedName("TotalQuestion")
@Expose
private Integer totalQuestion;
@SerializedName("Attempt")
@Expose
private Integer attempt;
@SerializedName("Correct")
@Expose
private Integer correct;
@SerializedName("Wrong")
@Expose
private Integer wrong;
@SerializedName("Review")
@Expose
private Integer review;
@SerializedName("TotalMarks")
@Expose
private String totalMarks;

public String getPaperID() {
return paperID;
}

public void setPaperID(String paperID) {
this.paperID = paperID;
}

public String getUserID() {
return userID;
}

public void setUserID(String userID) {
this.userID = userID;
}

public String getOrgID() {
return orgID;
}

public void setOrgID(String orgID) {
this.orgID = orgID;
}

public Integer getTotalQuestion() {
return totalQuestion;
}

public void setTotalQuestion(Integer totalQuestion) {
this.totalQuestion = totalQuestion;
}

public Integer getAttempt() {
return attempt;
}

public void setAttempt(Integer attempt) {
this.attempt = attempt;
}

public Integer getCorrect() {
return correct;
}

public void setCorrect(Integer correct) {
this.correct = correct;
}

public Integer getWrong() {
return wrong;
}

public void setWrong(Integer wrong) {
this.wrong = wrong;
}

public Integer getReview() {
return review;
}

public void setReview(Integer review) {
this.review = review;
}

public String getTotalMarks() {
return totalMarks;
}

public void setTotalMarks(String totalMarks) {
this.totalMarks = totalMarks;
}

}