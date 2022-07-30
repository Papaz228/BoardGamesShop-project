package com.company.boardgamesshop.database.dao.impl;
import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.entity.Order;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.database.dao.interfaces.OrderDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class OrderDaoImpl implements OrderDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String SELECT_LAST_ID_FROM_ORDER = "SELECT id FROM orders ORDER BY id DESC LIMIT 1";
    private static final String INSERT_INTO_ORDER = "INSERT INTO orders (total_cost, date_start, user_id, status_id) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_STATUS_ORDER = "UPDATE orders SET status_id = ? WHERE id = ?";
    private static final String SELECT_ORDERS_BY_USER_ID = "SELECT orders.id, orders.user_id, orders.status_id, orders.date_start, orders.total_cost, status.name FROM orders INNER JOIN status ON orders.status_id=status.id WHERE orders.user_id=  ?";
    private static final String SELECT_USER_ORDER_STATUS="SELECT orders.id, orders.user_id, orders.status_id, orders.date_start, orders.total_cost, status.name, users.email, status.local_id FROM orders INNER JOIN status ON orders.status_id=status.id INNER JOIN users ON orders.user_id=users.id";
    @Override
    public void createOrder(Order order) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_INTO_ORDER)) {
            preparedStatement.setDouble(1, order.getTotalCost());
            preparedStatement.setDate(2,  order.getDateStart());
            preparedStatement.setLong(3, order.getUserId());
            preparedStatement.setLong(4, order.getStatusId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }}
    @Override
    public Long takeLastID() {
        long lastId = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_LAST_ID_FROM_ORDER)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lastId = rs.getLong(Constant.ID);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return lastId;
    }
    @Override
    public ArrayList<ArrayList<String>> getFromOrdersAndUsersAndStatus() {
        ArrayList<ArrayList<String>> orders =new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement(SELECT_USER_ORDER_STATUS)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList<String> order =new ArrayList<>();
                order.add(String.valueOf(rs.getLong(Constant.ID)));
                order.add(String.valueOf(rs.getTimestamp(Constant.DATE_START_TABLE)));
                order.add(rs.getString(Constant.EMAIL));
                order.add(rs.getString(Constant.NAME));
                order.add(rs.getString(Constant.LOCAL_ID_TABLE));
                orders.add(order);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return orders;
    }
    @Override
    public void changeOrderStatus(Long orderId, Long statusId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_STATUS_ORDER)) {
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }}
    @Override
    public List<Order> getOrderByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_ORDERS_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong(Constant.ID));
                order.setTotalCost(rs.getInt(Constant.TOTAL_COST_TABLE));
                order.setDateStart(rs.getDate(Constant.DATE_START_TABLE));
                order.setUserId(rs.getLong(Constant.USER_ID_TABLE));
                order.setStatusId(rs.getLong(Constant.STATUS_ID_TABLE));
                order.setStatusName(rs.getString(Constant.NAME));
                orders.add(order);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return orders;
    }}

