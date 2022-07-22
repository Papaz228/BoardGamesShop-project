package com.company.boardgamesshop.database.dao.interfaces;
import com.company.boardgamesshop.entity.Basket;
import java.util.List;
public interface BasketDao {
    void addProductToBasket(Basket cart);
    List<Long> getProductsIdInBasket(Long userId);
    void deleteProductInBasket(Long productId, Long userId);
    List<Basket> getAllFromBasket(long productId);
    void deleteProductFromBasketByUserId(Long userId);
    Integer countOfBasketByUserId(Long userId, Long productId);
}
