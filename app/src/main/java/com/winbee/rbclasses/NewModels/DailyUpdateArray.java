package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DailyUpdateArray implements Serializable {

    @SerializedName("ItemId")
    @Expose
    private String itemId;
    @SerializedName("ItemType")
    @Expose
    private String itemType;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("IsAttachment")
    @Expose
    private Boolean isAttachment;
    @SerializedName("ItemAttachment")
    @Expose
    private String itemAttachment;
    @SerializedName("ItemAttachmentType")
    @Expose
    private String itemAttachmentType;
    @SerializedName("DisplayDate")
    @Expose
    private String displayDate;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Boolean getIsAttachment() {
        return isAttachment;
    }

    public void setIsAttachment(Boolean isAttachment) {
        this.isAttachment = isAttachment;
    }

    public String getItemAttachment() {
        return itemAttachment;
    }

    public void setItemAttachment(String itemAttachment) {
        this.itemAttachment = itemAttachment;
    }

    public String getItemAttachmentType() {
        return itemAttachmentType;
    }

    public void setItemAttachmentType(String itemAttachmentType) {
        this.itemAttachmentType = itemAttachmentType;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

}
