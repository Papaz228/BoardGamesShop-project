package com.company.boardgamesshop.entity;
public class Product {
    private Long id;
    private String name;
    private String description;
    private Integer cost;
    private Integer count;
    private Long countryId;
    private Long productCategoryId;
    private Boolean isActive;
    private String photoUrl;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getCost() {
        return cost;
    }
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Long getCountryId() {
        return countryId;
    }
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
    public Long getProductCategoryId() {
        return productCategoryId;
    }
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
    public Boolean isActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", count=" + count +
                ", countryId=" + countryId +
                ", productCategoryId=" + productCategoryId +
                '}';
    }
}
