package com.company.boardgamesshop.entity;
import java.sql.Date;

public class Order {
    private Long id;
    private Integer totalCost;
    private Date dateStart;
    private Date dateFinish;
    private Long userId;
    private Long statusId;
    private String statusName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }
    public Date getDateStart() {
        return dateStart;
    }
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    public Date getDateFinish() {
        return dateFinish;
    }
    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalCost=" + totalCost +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", userId=" + userId +
                ", statusId=" + statusId +
                '}';
    }
}
