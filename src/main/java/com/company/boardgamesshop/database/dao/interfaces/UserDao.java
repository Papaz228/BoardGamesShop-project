package com.company.boardgamesshop.database.dao.interfaces;
import com.company.boardgamesshop.entity.User;
import java.util.List;
public interface UserDao {
    void addUser(User user);
    List<User> getUsers();
    User getUserByLoginPassword(String login,String password);
    void bannedUser(Long userId, boolean isBanned);
    boolean isEmailExist(String email);
    void changePassword(Long userId, String newPassword);
}
