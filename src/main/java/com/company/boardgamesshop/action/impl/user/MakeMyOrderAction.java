package com.company.boardgamesshop.action.impl.user;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.BasketDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.BasketDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class MakeMyOrderAction implements Action {
    BasketDao basketDao = new BasketDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.USER);
        if (currentUser != null) {
            long userId = currentUser.getId();
            List<Long> productIdsInCart = basketDao.getProductsIdInBasket(userId);
            double totalPrice = 0.0;
            for (long productId : productIdsInCart) {
                Product product = productDao.getProductById(productId);
                Integer count = basketDao.countOfBasketByUserIdAndProductId(userId, product.getId());
                product.setCount(count);
                totalPrice += (double) product.getCost() * product.getCount();
            }
            request.setAttribute(Constant.TOTAL_PRICE, totalPrice);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.ORDER_JSP);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }
}
