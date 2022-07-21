package com.company.boardgamesshop.database.dao.interfaces;
import com.company.boardgamesshop.entity.ProductCategory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public interface ProductCategoryDao {
    void create(ProductCategory productCategory) throws SQLException, IOException;
    List<ProductCategory> getAllProductCategoriesByLocalId(Long localId) throws SQLException, IOException;
    void createAll(List<ProductCategory> productCategories)throws SQLException,IOException;
}
