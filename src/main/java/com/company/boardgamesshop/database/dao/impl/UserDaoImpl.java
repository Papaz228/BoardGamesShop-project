package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.UserDao;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String INSERT_INTO_USERS = "INSERT INTO users (first_name, last_name, birthday, phone_number, email, password, is_admin, is_banned) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_LOGIN_PASSWORD = "SELECT id, first_name, last_name, birthday,phone_number, email, password, is_admin, is_banned FROM users WHERE email = ? AND password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users WHERE is_admin = false";
    private static final String UPDATE_USER_ACTIVITY = "UPDATE users SET is_banned = ? WHERE id = ?";
    private static final String CHECK_LOGIN = "SELECT * FROM users WHERE email = ?";
    private static final String CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    @Override
    public void addUser(User user) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_INTO_USERS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getBirthday());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setBoolean(7, user.isAdmin());
            preparedStatement.setBoolean(8, user.isBanned());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public User getUserByLoginPassword(String login, String password) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_USER_BY_LOGIN_PASSWORD)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(Constant.ID));
                user.setFirstName(resultSet.getString(Constant.FIRST_NAME_TABLE));
                user.setLastName(resultSet.getString(Constant.LAST_NAME_TABLE));
                user.setBirthday(resultSet.getString(Constant.BIRTHDAY_TABLE));
                user.setPhoneNumber(resultSet.getString(Constant.PHONE_NUMBER_TABLE));
                user.setEmail(resultSet.getString(Constant.EMAIL));
                user.setPassword(resultSet.getString(Constant.PASSWORD));
                user.setIsAdmin(resultSet.getBoolean(Constant.IS_ADMIN_TABLE));
                user.setBanned(resultSet.getBoolean(Constant.IS_BANNED_TABLE));
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return user;
    }

    @Override
    public boolean isEmailExist(String email) {
        boolean isExist = false;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(CHECK_LOGIN)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isExist = true;
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return isExist;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(Constant.ID));
                user.setFirstName(resultSet.getString(Constant.FIRST_NAME_TABLE));
                user.setLastName(resultSet.getString(Constant.LAST_NAME_TABLE));
                user.setEmail(resultSet.getString(Constant.EMAIL));
                user.setPassword(resultSet.getString(Constant.PASSWORD));
                user.setIsAdmin(resultSet.getBoolean(Constant.IS_ADMIN_TABLE));
                user.setBanned(resultSet.getBoolean(Constant.IS_BANNED_TABLE));
                users.add(user);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return users;
    }

    @Override
    public void bannedUser(Long userId, boolean isBanned) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_USER_ACTIVITY)) {
            preparedStatement.setBoolean(1, isBanned);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(CHANGE_PASSWORD)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
    }
}