package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIADDSolutionSectionModel {
    @SerializedName("C")
    @Expose
    private Integer c;
    @SerializedName("N")
    @Expose
    private String n;

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

}

