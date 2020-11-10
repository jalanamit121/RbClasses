package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIADDSolutionDataModel {
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
    @SerializedName("IsAnswered")
    @Expose
    private Integer isAnswered;
    @SerializedName("IsCorrect")
    @Expose
    private Integer isCorrect;
    @SerializedName("IsWrong")
    @Expose
    private Integer isWrong;
    @SerializedName("IsReviewd")
    @Expose
    private Integer isReviewd;
    @SerializedName("UserAnswer")
    @Expose
    private String userAnswer;
    @SerializedName("CorrectAnswer")
    @Expose
    private String correctAnswer;
    @SerializedName("CorrectAnswer_img")
    @Expose
    private String correctAnswer_img;
    @SerializedName("AnswerDetails")
    @Expose
    private String answerDetails;
    @SerializedName("AnswerDetails_img")
    @Expose
    private String answerDetails_img;

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

    public Integer getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Integer isAnswered) {
        this.isAnswered = isAnswered;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getIsWrong() {
        return isWrong;
    }

    public void setIsWrong(Integer isWrong) {
        this.isWrong = isWrong;
    }

    public Integer getIsReviewd() {
        return isReviewd;
    }

    public void setIsReviewd(Integer isReviewd) {
        this.isReviewd = isReviewd;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer_img() {
        return correctAnswer_img;
    }

    public void setCorrectAnswer_img(String correctAnswer_img) {
        this.correctAnswer_img = correctAnswer_img;
    }

    public String getAnswerDetails() {
        return answerDetails;
    }

    public void setAnswerDetails(String answerDetails) {
        this.answerDetails = answerDetails;
    }

    public String getAnswerDetails_img() {
        return answerDetails_img;
    }

    public void setAnswerDetails_img(String answerDetails_img) {
        this.answerDetails_img = answerDetails_img;
    }

}