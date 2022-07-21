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
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_INTO_USERS = "INSERT INTO \"BoardGames\".\"User\"\n" +
            "(first_name, last_name, birthday, phone_number, email, \"password\", is_admin, is_banned)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_LOGIN_PASSWORD = "SELECT id, first_name, last_name, birthday,phone_number, email, password, is_admin, is_banned FROM" +
            " \"BoardGames\".\"User\" WHERE email = ? AND password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM \"BoardGames\".\"User\" WHERE is_admin = false";
    private static final String UPDATE_USER_ACTIVITY = "UPDATE \"BoardGames\".\"User\" SET  is_banned = ? WHERE id = ?";
    private static final String CHECK_LOGIN = "SELECT * FROM \"BoardGames\".\"User\" WHERE email = ?";
    public void addUser(User user) {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(INSERT_INTO_USERS)) {
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
        }
        finally {
            connectionPool.freeConn(con);
        }
    }
    public User getUserByLoginPassword(String login, String password) {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        User user=null;
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_USER_BY_LOGIN_PASSWORD)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 user = new User();
                user.setId(resultSet.getLong(Constant.ID));
                user.setFirstName(resultSet.getString(Constant.FIRST_NAME_TABLE));
                user.setLastName(resultSet.getString(Constant.LAST_NAME_TABLE));
                user.setBirthday(resultSet.getString("birthday"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setEmail(resultSet.getString(Constant.EMAIL));
                user.setPassword(resultSet.getString(Constant.PASSWORD));
                user.setIsAdmin(resultSet.getBoolean("is_admin"));
                user.setBanned(resultSet.getBoolean("is_banned"));
            }
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return user;
    }
    public boolean isEmailExist(String email) {
        boolean isExist = false;
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement preparedStatement = con.prepareStatement(CHECK_LOGIN)){
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isExist = true;
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return isExist;
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(Constant.ID));
                user.setFirstName(resultSet.getString(Constant.FIRST_NAME_TABLE));
                user.setLastName(resultSet.getString(Constant.LAST_NAME_TABLE));
                user.setEmail(resultSet.getString(Constant.EMAIL));
                user.setPassword(resultSet.getString(Constant.PASSWORD));
                user.setIsAdmin(resultSet.getBoolean("is_admin"));
                user.setBanned(resultSet.getBoolean("is_banned"));
                users.add(user);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return users;
    }
    public void BannedUser(Long userId,boolean isBanned) {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement preparedStatement = con.prepareStatement(UPDATE_USER_ACTIVITY)){
            preparedStatement.setBoolean(1,isBanned);
            preparedStatement.setLong(2,userId);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }
    public void changePassword(Long userId, String newPassword){
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement=con.prepareStatement("UPDATE \"BoardGames\".\"User\" SET password = ? WHERE id=?")){
            preparedStatement.setString(1,newPassword);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }
}