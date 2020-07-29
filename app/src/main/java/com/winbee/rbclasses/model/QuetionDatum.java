package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuetionDatum implements Serializable {

    @SerializedName("DATE")
    @Expose
    private String dATE;
    @SerializedName("QuestionId")
    @Expose
    private String questionId;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("User")
    @Expose
    private String user;
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
    @SerializedName("Is_Attempted")
    @Expose
    private Boolean is_Attempted;

    public String getDATE() {
        return dATE;
    }

    public void setDATE(String dATE) {
        this.dATE = dATE;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public Boolean getIs_Attempted() {
        return is_Attempted;
    }

    public void setIs_Attempted(Boolean is_Attempted) {
        this.is_Attempted = is_Attempted;
    }

}