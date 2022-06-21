package com.company.boardgamesshop.action.common;

import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.database.dao.interfaces.UserDao;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.validator.Validator;
import com.company.boardgamesshop.database.dao.impl.UserDaoImpl;
import com.company.boardgamesshop.action.factory.FactoryAction;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class LoginAction implements Action {

    private FactoryAction factoryAction = FactoryAction.getInstance();
    private UserDao userDao = new UserDaoImpl();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();


        String login = request.getParameter(Constant.EMAIL);
        String password = request.getParameter(Constant.PASSWORD);
        User u=(User) session.getAttribute(Constant.USER);
       if(u==null) {
           if (login != null && password != null) {
               String securedPassword = DigestUtils.md5Hex(password);
               User user = userDao.getUserByLoginPassword(login, securedPassword);

               if (user != null) {
                   if (Validator.checkAccess(user)) {
                       session.setAttribute(Constant.USER, user);
                       session.setAttribute(Constant.ADMIN, user.isAdmin());
                       session.setAttribute("localId", 2L);
                       response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
                   } else {
                       request.setAttribute(Constant.ERROR, Constant.ERROR_USER_BLOCKED);
                       dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.LOGIN_JSP);
                       dispatcher.forward(request, response);
                   }
               } else {
                   request.setAttribute(Constant.ERROR, Constant.ERROR_EMAIL_OR_PASSWORD);
                   dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.LOGIN_JSP);
                   dispatcher.forward(request, response);
               }

           }
           else {
               dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.LOGIN_JSP);
               dispatcher.forward(request, response);
           }
       }
       else {
           response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
       }
    }
}
