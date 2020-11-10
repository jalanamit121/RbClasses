package com.winbee.rbclasses.model;

public class SIADDataModel {
    private SIADDQuestionDataModel[] QuestionData;
    private SIADDQuestionSectionModel[] QuestionSection;

    public SIADDataModel( SIADDQuestionDataModel[] questionData, SIADDQuestionSectionModel[] questionSection) {
        QuestionData = questionData;
        QuestionSection = questionSection;
    }

    public SIADDQuestionDataModel[] getQuestionData() {
        return QuestionData;
    }

    public SIADDQuestionSectionModel[] getQuestionSection() {
        return QuestionSection;
    }
}
