package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class McqAskedQuestionModel implements Serializable {

@SerializedName("Response")
@Expose
private Boolean response;
@SerializedName("Message")
@Expose
private Object message;
@SerializedName("QuetionData")
@Expose
private QuetionDatum[] QuetionData;

    public McqAskedQuestionModel(Boolean response, Object message, QuetionDatum[] quetionData) {
        this.response = response;
        this.message = message;
        QuetionData = quetionData;
    }

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

public QuetionDatum[] getData(){
return QuetionData;
    }

}