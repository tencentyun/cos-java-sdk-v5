package com.qcloud.cos.model;

import com.qcloud.cos.utils.Jackson;

import java.io.Serializable;

public class DecompressionResult implements Serializable {

  private String jobId;

  private String status;

  private String msg;

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return Jackson.toJsonString(this);
  }

}
