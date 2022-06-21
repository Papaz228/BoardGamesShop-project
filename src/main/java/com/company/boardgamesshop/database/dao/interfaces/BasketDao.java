package com.company.boardgamesshop.database.dao.interfaces;

import com.company.boardgamesshop.entity.Basket;
import com.company.boardgamesshop.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface BasketDao {
    void addProductToBasket(Basket cart)throws SQLException, IOException;
    List<Long> getProductsIdInBasket(Long userId) throws SQLException, IOException;
    void deleteProductInBasket(Long productId, Long userId) throws SQLException, IOException;
    Basket getBasket(Basket cart) throws SQLException, IOException;
    List<Basket> getAllFromBasket(long productId) throws SQLException, IOException;
    void deleteProductFromBasketByUserId(Long userId)throws SQLException, IOException;
    List<Product> getAllProductsInBasket(Long userId) throws SQLException, IOException;
    Integer countOfBasketByUserId(Long userId, Long productId) throws SQLException, IOException;
}
