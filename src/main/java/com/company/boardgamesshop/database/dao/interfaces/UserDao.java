package com.company.boardgamesshop.database.dao.interfaces;


import com.company.boardgamesshop.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void addUser(User user) throws SQLException, IOException;
    List<User> getUsers() throws SQLException, IOException;
    User getUserByLoginPassword(String login,String password) throws SQLException, IOException;
    void BannedUser(Long userId, boolean isBanned) throws SQLException, IOException;
    boolean isEmailExist(String email) throws SQLException, IOException;
    void changePassword(Long userId, String newPassword);
}
