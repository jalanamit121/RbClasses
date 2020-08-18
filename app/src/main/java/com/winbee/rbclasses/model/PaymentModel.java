package com.winbee.rbclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentModel implements Serializable {


  @SerializedName("RazorpayOrderId")
  @Expose
  private String razorpayOrderId;
  @SerializedName("Success")
  @Expose
  private Boolean success;
  @SerializedName("course_id")
  @Expose
  private String course_id;
  @SerializedName("user_id")
  @Expose
  private String user_id;
  @SerializedName("amount_org_id")
  @Expose
  private String amount_org_id;
  @SerializedName("org_id")
  @Expose
  private String org_id;
  @SerializedName("OrgOrderId")
  @Expose
  private String orgOrderId;

  public String getRazorpayOrderId() {
    return razorpayOrderId;
  }

  public void setRazorpayOrderId(String razorpayOrderId) {
    this.razorpayOrderId = razorpayOrderId;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getCourse_id() {
    return course_id;
  }

  public void setCourse_id(String course_id) {
    this.course_id = course_id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getAmount_org_id() {
    return amount_org_id;
  }

  public void setAmount_org_id(String amount_org_id) {
    this.amount_org_id = amount_org_id;
  }

  public String getOrg_id() {
    return org_id;
  }

  public void setOrg_id(String org_id) {
    this.org_id = org_id;
  }

  public String getOrgOrderId() {
    return orgOrderId;
  }

  public void setOrgOrderId(String orgOrderId) {
    this.orgOrderId = orgOrderId;
  }

}