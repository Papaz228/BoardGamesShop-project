package com.company.boardgamesshop.action.admin;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.OrderDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.OrderDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
public class ChangeStatusToOrderAction implements Action {
    OrderDao orderDao = new OrderDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        long orderId = Long.parseLong(request.getParameter(Constant.ORDER_ID));
        long statusId = Long.parseLong(request.getParameter(Constant.STATUS_ID));
        orderDao.changeOrderStatus(orderId, statusId);
        response.sendRedirect(ConstantPageNamesJSPAndAction.ORDERS_ADMIN);
    }
}
