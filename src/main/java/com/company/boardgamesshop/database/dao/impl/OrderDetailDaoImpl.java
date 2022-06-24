package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.OrderDetailDao;
import com.company.boardgamesshop.entity.OrderDetail;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class OrderDetailDaoImpl implements OrderDetailDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_INTO_ORDER_ITEM = "INSERT into \"BoardGames\".\"Order_detail\" (order_id, product_id, count, cost) VALUES(?,?,?,?)";

    @Override
    public void createOrderDetail(OrderDetail orderDetail) throws SQLException, IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement pstmt = con.prepareStatement(INSERT_INTO_ORDER_ITEM)){
            pstmt.setLong(1, orderDetail.getOrderId());
            pstmt.setLong(2, orderDetail.getProductId());
            pstmt.setInt(3,orderDetail.getCount());
            pstmt.setInt(4,orderDetail.getCost());
            pstmt.executeUpdate();
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }

}
