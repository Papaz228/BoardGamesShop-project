package com.company.boardgamesshop.action.impl.user;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.*;
import com.company.boardgamesshop.database.dao.interfaces.*;
import com.company.boardgamesshop.entity.*;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static com.company.boardgamesshop.validator.Validator.validateCVVWithRegex;
import static com.company.boardgamesshop.validator.Validator.validateCardNumberWithRegex;

public class CreateOrderFromMyBasketAction implements Action {
    BasketDao basketDao = new BasketDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
    StatusDao statusDao = new StatusDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    OrderDetailDao orderItemDao = new OrderDetailDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        User currentUser = (User) session.getAttribute(Constant.USER);
        if (currentUser == null) {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
        Long userId = currentUser != null ? currentUser.getId() : null;
        String bankCardCVV = request.getParameter(Constant.BANK_CVV);
        String bankCardNumber = request.getParameter(Constant.BANK_CARD_NUMBER);
        double totalPrice = Double.parseDouble(request.getParameter(Constant.TOTAL_COST));
        request.setAttribute(Constant.TOTAL_PRICE, totalPrice);
        if (bankCardCVV == null) {
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.ORDER_JSP);
            dispatcher.forward(request, response);
        } else if (!validateCardNumberWithRegex(bankCardNumber)) {
            request.setAttribute(Constant.ERROR, Constant.ERROR_CARD_FORMAT);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.ORDER_JSP);
            dispatcher.forward(request, response);
        } else if (!validateCVVWithRegex(bankCardCVV)) {
            request.setAttribute(Constant.ERROR, Constant.ERROR_CVV_FORMAT);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.ORDER_JSP);
            dispatcher.forward(request, response);
        } else {
            Status status = new Status();
            Long localId = (Long) session.getAttribute(Constant.LOCAL_ID);
            status.setLocalId(localId);
            if (localId == 1) {
                status.setId(statusDao.getIdByStatusName("Pending"));
                status.setName("Pending");
            } else {
                status.setId(statusDao.getIdByStatusName("Выполняется"));
                status.setName("Выполняется");
            }
            Order order = new Order();
            LocalDate now = LocalDate.now();
            Date date = Date.valueOf(now);
            order.setDateStart(date);
            order.setUserId(userId);
            order.setStatusId(status.getId());
            List<Long> productIdsInCart = basketDao.getProductsIdInBasket(userId);
            order.setTotalCost(totalPrice);
            orderDao.createOrder(order);
            Long orderId = orderDao.takeLastID();
            if (orderId == null || orderId == 0) orderId = 1L;
            for (Long productId : productIdsInCart) {
                OrderDetail orderDetail = new OrderDetail();
                Product product = productDao.getProductById(productId);
                Integer count = basketDao.countOfBasketByUserIdAndProductId(userId, product.getId());
                product.setCount(product.getCount() - count);
                productDao.updateProduct(product);
                product.setCount(count);
                orderDetail.setProductId(product.getId());
                orderDetail.setCost(product.getCost());
                orderDetail.setCount(product.getCount());
                orderDetail.setOrderId(orderId);
                orderItemDao.createOrderDetail(orderDetail);
            }
            basketDao.deleteProductFromBasketByUserId(userId);
            response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
        }
    }
}
