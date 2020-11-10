package com.winbee.rbclasses.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestSubscriptionArray implements Serializable {
    @SerializedName("DisplayPrice")
    @Expose
    private String displayPrice;
    @SerializedName("DiscountPrice")
    @Expose
    private String discountPrice;
    @SerializedName("TotalDiscount")
    @Expose
    private Integer totalDiscount;
    @SerializedName("Tenure")
    @Expose
    private String tenure;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("SubscriptionId")
    @Expose
    private String subscriptionId;

    public String getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(String displayPrice) {
        this.displayPrice = displayPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Integer totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

}