package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIADDQuestionSectionModel {
    @Expose
    @SerializedName("C")
    private String C;
    @Expose
    @SerializedName("N")
    private String N;

    public SIADDQuestionSectionModel(String c, String n) {
        C = c;
        N = n;
    }

    public String getC() {
        return C;
    }

    public String getN() {
        return N;
    }
}
