package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class McqQuestionSolutionModel implements Serializable {

@SerializedName("Response")
@Expose
private Boolean response;
@SerializedName("Message")
@Expose
private Object message;
@SerializedName("UserID")
@Expose
private String userID;
@SerializedName("QuestionID")
@Expose
private String questionID;
@SerializedName("Solution")
@Expose
private String solution;

public Boolean getResponse() {
return response;
}

public void setResponse(Boolean response) {
this.response = response;
}

public Object getMessage() {
return message;
}

public void setMessage(Object message) {
this.message = message;
}

public String getUserID() {
return userID;
}

public void setUserID(String userID) {
this.userID = userID;
}

public String getQuestionID() {
return questionID;
}

public void setQuestionID(String questionID) {
this.questionID = questionID;
}

public String getSolution() {
return solution;
}

public void setSolution(String solution) {
this.solution = solution;
}

}