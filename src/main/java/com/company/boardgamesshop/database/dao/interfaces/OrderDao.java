package com.company.boardgamesshop.database.dao.interfaces;

import com.company.boardgamesshop.entity.Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDao {
    void createOrder(Order order) throws SQLException, IOException;
    Long takeLastID() throws SQLException;
    ArrayList<ArrayList<String>> getFrom4Tables() throws SQLException, IOException;
    void changeOrderStatus(Long orderId, Long statusId) throws SQLException, IOException;
    List<Order> getOrderByUserId(Long userId) throws SQLException, IOException;
}
