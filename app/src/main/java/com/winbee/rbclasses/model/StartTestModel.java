package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StartTestModel implements Serializable {

@SerializedName("ExamStatus")
@Expose
private String examStatus;
@SerializedName("PaperID")
@Expose
private String paperID;
@SerializedName("UserID")
@Expose
private String userID;
@SerializedName("OrgID")
@Expose
private String orgID;
@SerializedName("Exam_Created")
@Expose
private Object exam_Created;
@SerializedName("Exam_Created_Message")
@Expose
private Object exam_Created_Message;

public String getExamStatus() {
return examStatus;
}

public void setExamStatus(String examStatus) {
this.examStatus = examStatus;
}

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

public Object getExam_Created() {
return exam_Created;
}

public void setExam_Created(Object exam_Created) {
this.exam_Created = exam_Created;
}

public Object getExam_Created_Message() {
return exam_Created_Message;
}

public void setExam_Created_Message(Object exam_Created_Message) {
this.exam_Created_Message = exam_Created_Message;
}

}