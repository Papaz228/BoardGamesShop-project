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
    private static final String SELECT_LAST_ID_FROM_ORDER = "SELECT id \n" +
            "FROM \"BoardGames\".\"Order\" \n" +
            "ORDER BY id DESC \n" +
            "LIMIT 1 ";
    private static final String INSERT_INTO_ORDER = "INSERT INTO \"BoardGames\".\"Order\"\n" +
            "(total_cost, date_start, user_id, status_id)\n" +
            "VALUES(?, ?, ?, ?);\n";
    private static final String UPDATE_STATUS_ORDER = "UPDATE \"BoardGames\".\"Order\" SET  status_id = ? WHERE id = ?";
    private static final String SELECT_ORDERS_BY_USER_ID = "SELECT \"BoardGames\".\"Order\".id,\"BoardGames\".\"Order\".user_id,\"BoardGames\".\"Order\".status_id, \"BoardGames\".\"Order\".date_start, \"BoardGames\".\"Order\".total_cost, \"BoardGames\".\"Status\".name FROM \"BoardGames\".\"Order\" INNER JOIN \"BoardGames\".\"Status\" ON \"BoardGames\".\"Order\".status_id=\"BoardGames\".\"Status\".id WHERE \"BoardGames\".\"Order\".user_id= ?;";
    private static final String SELECT_USER_ORDER_STATUS="SELECT \"BoardGames\".\"Order\".id,\"BoardGames\".\"Order\".user_id,\"BoardGames\".\"Order\".status_id, \"BoardGames\".\"Order\".date_start, \"BoardGames\".\"Order\".total_cost, \"BoardGames\".\"Status\".name, \"BoardGames\".\"User\".email,\"BoardGames\".\"Status\".local_id FROM \"BoardGames\".\"Order\" INNER JOIN \"BoardGames\".\"Status\" ON \"BoardGames\".\"Order\".status_id=\"BoardGames\".\"Status\".id INNER JOIN \"BoardGames\".\"User\" ON \"BoardGames\".\"Order\".user_id=\"BoardGames\".\"User\".id";
    @Override
    public void createOrder(Order order) {
        ConnectionPool connectionPool = ConnectionPool.getConnPool();
        Connection con = connectionPool.getConn();
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_INTO_ORDER)) {
            preparedStatement.setInt(1, order.getTotalCost());
            preparedStatement.setTimestamp(2,  order.getDateStart());
            preparedStatement.setLong(3, order.getUserId());
            preparedStatement.setLong(4, order.getStatusId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.freeConn(con);
        }}
    @Override
    public Long takeLastID() {
        long lastId = 0;
        ConnectionPool connectionPool = ConnectionPool.getConnPool();
        Connection con = connectionPool.getConn();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_LAST_ID_FROM_ORDER)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lastId = rs.getLong("id");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.freeConn(con);
        }
        return lastId;
    }
    @Override
    public ArrayList<ArrayList<String>> getFromOrdersAndUsersAndStatus() {
        ArrayList<ArrayList<String>> orders =new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getConnPool();
        Connection con = connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(SELECT_USER_ORDER_STATUS)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList<String> order =new ArrayList<>();
                order.add(String.valueOf(rs.getLong("id")));
                order.add(String.valueOf(rs.getTimestamp("date_start")));
                order.add(rs.getString("email"));
                order.add(rs.getString("name"));
                order.add(rs.getString("local_id"));
                orders.add(order);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.freeConn(con);
        }
        return orders;
    }
    @Override
    public void changeOrderStatus(Long orderId, Long statusId) {
        ConnectionPool connectionPool = ConnectionPool.getConnPool();
        Connection con = connectionPool.getConn();
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_STATUS_ORDER)) {
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.freeConn(con);
        }}
    @Override
    public List<Order> getOrderByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getConnPool();
        Connection con = connectionPool.getConn();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_ORDERS_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong(Constant.ID));
                order.setTotalCost(rs.getInt("total_cost"));
                order.setDateStart(rs.getTimestamp("date_start"));
                order.setUserId(rs.getLong("user_id"));
                order.setStatusId(rs.getLong("status_id"));
                order.setStatusName(rs.getString("name"));
                orders.add(order);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.freeConn(con);
        }
        return orders;
    }}

