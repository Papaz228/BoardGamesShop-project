package com.company.boardgamesshop.database.dao.interfaces;
import com.company.boardgamesshop.entity.Order;
import java.util.ArrayList;
import java.util.List;
public interface OrderDao {
    void createOrder(Order order);
    Long takeLastID();
    ArrayList<ArrayList<String>> getFromOrdersAndUsersAndStatus();
    void changeOrderStatus(Long orderId, Long statusId);
    List<Order> getOrderByUserId(Long userId);
}
