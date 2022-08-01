package com.company.boardgamesshop.database.dao.interfaces;

import com.company.boardgamesshop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategory> getAllProductCategoriesByLocalId(Long localId);

    void createAll(List<ProductCategory> productCategories);

    void updateProductCategory(ProductCategory productCategory);
}
