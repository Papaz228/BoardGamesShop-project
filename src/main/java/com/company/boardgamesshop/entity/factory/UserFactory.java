package com.company.boardgamesshop.entity.factory;

import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;

public class UserFactory {
    private static UserFactory instance = new UserFactory();

    public static UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }

    public User fillUser(HttpServletRequest request) {
        User newUser = new User();
        newUser.setFirstName(request.getParameter(Constant.FIRST_NAME_TABLE));
        newUser.setLastName(request.getParameter(Constant.LAST_NAME_TABLE));
        newUser.setBirthday(request.getParameter(Constant.BIRTHDAY_TABLE));
        newUser.setPhoneNumber(request.getParameter(Constant.PHONE_NUMBER_TABLE));
        newUser.setEmail(request.getParameter(Constant.EMAIL));
        String password = request.getParameter(Constant.PASSWORD);
        String securedPassword = DigestUtils.md5Hex(password);
        newUser.setPassword(securedPassword);
        newUser.setIsAdmin(false);
        newUser.setBanned(false);
        return newUser;
    }
}