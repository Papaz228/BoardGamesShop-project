package com.company.boardgamesshop.action.admin;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.OrderDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.OrderDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
public class ListOfOrdersForAdminAction implements Action {
    OrderDao orderDao = new OrderDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        User currentUser = (User)session.getAttribute(Constant.USER);
        if(currentUser.isAdmin()){
            ArrayList<ArrayList<String>> orders = orderDao.getFrom4Tables();
            request.setAttribute(Constant.ORDERS, orders);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.ORDERS_ADMIN_JSP);
            dispatcher.forward(request, response);
        }else {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }
}
