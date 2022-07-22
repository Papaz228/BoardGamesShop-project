package com.company.boardgamesshop.database.dao.interfaces;
import com.company.boardgamesshop.entity.Product;
import java.util.List;
public interface ProductDao {
    List<Product> getAllProduct();
    Product getProductById(Long id);
    void createProduct(Product product);
    void updateProduct(Product product);
    void deactivateProduct(Long productId, boolean isActive);
}
