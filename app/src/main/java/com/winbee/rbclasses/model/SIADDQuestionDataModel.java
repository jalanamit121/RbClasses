package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIADDQuestionDataModel {
    @SerializedName("SectionCode")
    @Expose
    private String sectionCode;
    @SerializedName("QuestionID")
    @Expose
    private String questionID;
    @SerializedName("QuestionTitle")
    @Expose
    private String questionTitle;
    @SerializedName("QuestionTitle_img")
    @Expose
    private String questionTitle_img;
    @SerializedName("Option1")
    @Expose
    private String option1;
    @SerializedName("Option1_img")
    @Expose
    private String option1_img;
    @SerializedName("Option2")
    @Expose
    private String option2;
    @SerializedName("Option2_img")
    @Expose
    private String option2_img;
    @SerializedName("Option3")
    @Expose
    private String option3;
    @SerializedName("Option3_img")
    @Expose
    private String option3_img;
    @SerializedName("Option4")
    @Expose
    private String option4;
    @SerializedName("Option4_img")
    @Expose
    private String option4_img;
    @SerializedName("QuestionGUID")
    @Expose
    private String questionGUID;


    public SIADDQuestionDataModel(String sectionCode, String questionID, String questionTitle, String questionTitle_img, String option1, String option1_img, String option2, String option2_img, String option3, String option3_img, String option4, String option4_img, String questionGUID) {
        this.sectionCode = sectionCode;
        this.questionID = questionID;
        this.questionTitle = questionTitle;
        this.questionTitle_img = questionTitle_img;
        this.option1 = option1;
        this.option1_img = option1_img;
        this.option2 = option2;
        this.option2_img = option2_img;
        this.option3 = option3;
        this.option3_img = option3_img;
        this.option4 = option4;
        this.option4_img = option4_img;
        this.questionGUID = questionGUID;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionTitle_img() {
        return questionTitle_img;
    }

    public void setQuestionTitle_img(String questionTitle_img) {
        this.questionTitle_img = questionTitle_img;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption1_img() {
        return option1_img;
    }

    public void setOption1_img(String option1_img) {
        this.option1_img = option1_img;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption2_img() {
        return option2_img;
    }

    public void setOption2_img(String option2_img) {
        this.option2_img = option2_img;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption3_img() {
        return option3_img;
    }

    public void setOption3_img(String option3_img) {
        this.option3_img = option3_img;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption4_img() {
        return option4_img;
    }

    public void setOption4_img(String option4_img) {
        this.option4_img = option4_img;
    }

    public String getQuestionGUID() {
        return questionGUID;
    }

    public void setQuestionGUID(String questionGUID) {
        this.questionGUID = questionGUID;
    }

}