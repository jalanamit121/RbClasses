package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TxnModel implements Serializable {

@SerializedName("BucketName")
@Expose
private String bucketName;
@SerializedName("order_id")
@Expose
private String order_id;
@SerializedName("amount_order_org")
@Expose
private String amount_order_org;
@SerializedName("InitaitedDate")
@Expose
private String initaitedDate;
@SerializedName("SuccessDate")
@Expose
private String successDate;
@SerializedName("Paid")
@Expose
private String paid;

public String getBucketName() {
return bucketName;
}

public void setBucketName(String bucketName) {
this.bucketName = bucketName;
}

public String getOrder_id() {
return order_id;
}

public void setOrder_id(String order_id) {
this.order_id = order_id;
}

public String getAmount_order_org() {
return amount_order_org;
}

public void setAmount_order_org(String amount_order_org) {
this.amount_order_org = amount_order_org;
}

public String getInitaitedDate() {
return initaitedDate;
}

public void setInitaitedDate(String initaitedDate) {
this.initaitedDate = initaitedDate;
}

public String getSuccessDate() {
return successDate;
}

public void setSuccessDate(String successDate) {
this.successDate = successDate;
}

public String getPaid() {
return paid;
}

public void setPaid(String paid) {
this.paid = paid;
}

}