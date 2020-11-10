package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentQAModel {
    @Expose
    @SerializedName("SectionCode")
    public String SectionCode;
    @Expose
    @SerializedName("QuestionID")
    public String QuestionID;
    @Expose
    @SerializedName("QuestionGUID")
    public String QuestionGUID;
    @Expose
    @SerializedName("ansStatus")
    public String ansStatus;
    @Expose
    @SerializedName("ansStatusCode")
    public String ansStatusCode;
    @Expose
    @SerializedName("selectedAns")
    public String selectedAns;
    //ansStatus answered,not_answered,review,not_visited,review_and_answered
    //1-submitand next,2-next,3-review,4-submitand review,0-not_visited

    public String getSectionCode() {
        return SectionCode;
    }

    public void setSectionCode(String sectionCode) {
        SectionCode = sectionCode;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(String selectedAns) {
        this.selectedAns = selectedAns;
    }

    public String getQuestionGUID() {
        return QuestionGUID;
    }

    public void setQuestionGUID(String questionGUID) {
        QuestionGUID = questionGUID;
    }

    public String getAnsStatus() {
        return ansStatus;
    }

    public void setAnsStatus(String ansStatus) {
        this.ansStatus = ansStatus;
    }

    public String getAnsStatusCode() {
        return ansStatusCode;
    }

    public void setAnsStatusCode(String ansStatusCode) {
        this.ansStatusCode = ansStatusCode;
    }
}
