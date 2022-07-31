package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.OrderDetailDao;
import com.company.boardgamesshop.entity.OrderDetail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDetailDaoImpl implements OrderDetailDao {
    private static final String INSERT_INTO_ORDER_ITEM = "INSERT into order_detail (order_id, product_id, count, cost) VALUES(?,?,?,?)";
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createOrderDetail(OrderDetail orderDetail) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_INTO_ORDER_ITEM)) {
            preparedStatement.setLong(1, orderDetail.getOrderId());
            preparedStatement.setLong(2, orderDetail.getProductId());
            preparedStatement.setInt(3, orderDetail.getCount());
            preparedStatement.setInt(4, orderDetail.getCost());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
    }
}
