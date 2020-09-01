package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LiveChatMessageFetch implements Serializable {

@SerializedName("Response")
@Expose
private Boolean response;
@SerializedName("ResponseMessage")
@Expose
private Object responseMessage;
@SerializedName("Messages")
@Expose
private Message[] Messages;

    public LiveChatMessageFetch(Boolean response, Object responseMessage, Message[] messages) {
        this.response = response;
        this.responseMessage = responseMessage;
        Messages = messages;
    }

    public Boolean getResponse() {
return response;
}

public void setResponse(Boolean response) {
this.response = response;
}

public Object getResponseMessage() {
return responseMessage;
}

public void setResponseMessage(Object responseMessage) {
this.responseMessage = responseMessage;
}

    public Message[] getMessages(){
        return Messages;
    }

}
