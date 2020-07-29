package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class McqQuestionModel  implements Serializable {

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
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("QuestionTitle")
    @Expose
    private String questionTitle;
    @SerializedName("Opt1")
    @Expose
    private String opt1;
    @SerializedName("Opt2")
    @Expose
    private String opt2;
    @SerializedName("Opt3")
    @Expose
    private String opt3;
    @SerializedName("Opt4")
    @Expose
    private String opt4;
    @SerializedName("SolutionFlag")
    @Expose
    private String solutionFlag;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getSolutionFlag() {
        return solutionFlag;
    }

    public void setSolutionFlag(String solutionFlag) {
        this.solutionFlag = solutionFlag;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

}