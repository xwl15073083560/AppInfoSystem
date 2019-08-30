package cn.appsys.pojo;


import java.util.Date;

public class AdPromotion {

  private Integer id;
  private Integer appId;
  private String adPicPath;
  private Integer adPv;
  private Integer carouselPosition;
  private Date startTime;
  private Date endTime;
  private Integer createdBy;
  private Date creationDate;
  private Integer modifyBy;
  private Date modifyDate;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getAppId() {
    return appId;
  }

  public void setAppId(Integer appId) {
    this.appId = appId;
  }


  public String getAdPicPath() {
    return adPicPath;
  }

  public void setAdPicPath(String adPicPath) {
    this.adPicPath = adPicPath;
  }


  public Integer getAdPv() {
    return adPv;
  }

  public void setAdPv(Integer adPv) {
    this.adPv = adPv;
  }


  public Integer getCarouselPosition() {
    return carouselPosition;
  }

  public void setCarouselPosition(Integer carouselPosition) {
    this.carouselPosition = carouselPosition;
  }


  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }


  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }


  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }


  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }


  public Integer getModifyBy() {
    return modifyBy;
  }

  public void setModifyBy(Integer modifyBy) {
    this.modifyBy = modifyBy;
  }


  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

}
