package com.winbee.rbclasses.model;

public class SIADSolutionDataModel {

    private SIADDSolutionDataModel[] QuestionData;
    private SIADDSolutionSectionModel[] QuestionSection;

    public SIADSolutionDataModel( SIADDSolutionDataModel[] questionData, SIADDSolutionSectionModel[] questionSection) {
        QuestionData = questionData;
        QuestionSection = questionSection;
    }

    public SIADDSolutionDataModel[] getQuestionData() {
        return QuestionData;
    }

    public SIADDSolutionSectionModel[] getQuestionSection() {
        return QuestionSection;
    }
}
