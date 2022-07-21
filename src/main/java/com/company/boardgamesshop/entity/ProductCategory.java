package com.company.boardgamesshop.entity;
public class ProductCategory {
    private Long id;
    private Long localId;
    private String categoryName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getLocalId() {
        return localId;
    }
    public void setLocalId(Long localId) {
        this.localId = localId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    @Override
    public String toString() {
        return "Product_category{" +
                "id=" + id +
                ", localId=" + localId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
