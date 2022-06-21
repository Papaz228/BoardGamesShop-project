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
    private static final String SELECT_PRODUCT_ID = "SELECT product_id FROM \"BoardGames\".\"Order_detail\" WHERE order_id = ?";
    private static final String DELETE_ORDER_ITEMS = "DELETE FROM \"BoardGames\".\"Order_detail\" WHERE order_id = ?";

    @Override
    public void createOrderDetail(OrderDetail orderDetail) throws SQLException, IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement pstmt = con.prepareStatement(INSERT_INTO_ORDER_ITEM);){
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

    @Override
    public ArrayList<Long> getProductsIdFromOrderDetail(Long orderId ) throws SQLException, IOException {
        ArrayList<Long> productsId =new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try (PreparedStatement pstmt = con.prepareStatement(SELECT_PRODUCT_ID)){
            pstmt.setLong(1,orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                productsId.add(rs.getLong(Constant.PRODUCT_ID_TABLE));
            }

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return productsId;
    }

    @Override
    public void deleteOrderDetailByOrderId(Long orderId) throws SQLException, IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER_ITEMS)){
            pstmt.setLong(1, orderId);
          pstmt.executeUpdate();

        }catch (SQLException e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }
}
