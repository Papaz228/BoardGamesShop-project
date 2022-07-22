package com.company.boardgamesshop.action.impl.admin;
import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.interfaces.UserDao;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.UserDaoImpl;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
public class CheckAllUsersAction implements Action {
    UserDao userDao = new UserDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        User currentUser = (User)session.getAttribute(Constant.USER);
        if(currentUser.isAdmin()){
            List<User> users = userDao.getUsers();
            request.setAttribute(Constant.USERS, users);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.ALL_USERS_JSP);
            dispatcher.forward(request, response);
        }else {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }}
