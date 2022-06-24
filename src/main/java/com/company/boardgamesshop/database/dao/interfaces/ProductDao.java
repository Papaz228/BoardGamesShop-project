package com.company.boardgamesshop.database.dao.interfaces;

import com.company.boardgamesshop.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProduct() throws SQLException, IOException;
    Product getProductById(Long id) throws SQLException, IOException;
    void createProduct(Product product) throws SQLException, IOException;
    void updateProduct(Product product) throws SQLException, IOException;
    void deactivateProduct(Long productId, boolean isActive)throws SQLException, IOException;
}
