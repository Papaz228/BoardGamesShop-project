package com.company.boardgamesshop.entity.factory;

import javax.servlet.http.HttpServletRequest;

import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.commons.codec.digest.DigestUtils;
public class UserFactory {
    private static UserFactory instance = new UserFactory();
    public User fillUser(HttpServletRequest request) {
        User newUser = new User();
        newUser.setFirstName(request.getParameter("first_name"));
        newUser.setLastName(request.getParameter("last_name"));
        newUser.setBirthday(request.getParameter("birthday"));
        newUser.setPhoneNumber(request.getParameter("phone_number"));
        newUser.setEmail(request.getParameter("email"));
        String password = request.getParameter(Constant.PASSWORD);
        String securedPassword = DigestUtils.md5Hex(password);
        newUser.setPassword(securedPassword);
        newUser.setIsAdmin(false);
        newUser.setBanned(false);
        return newUser;
    }
    public static UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }
}