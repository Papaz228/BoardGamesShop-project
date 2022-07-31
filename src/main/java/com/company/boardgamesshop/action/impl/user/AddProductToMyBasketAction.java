package com.company.boardgamesshop.action.impl.user;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.BasketDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.BasketDao;
import com.company.boardgamesshop.entity.Basket;
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

public class AddProductToMyBasketAction implements Action {
    BasketDao basketDao = new BasketDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        long productId = Long.parseLong(request.getParameter(Constant.PRODUCT_ID));
        Integer productCount = Integer.parseInt(request.getParameter(Constant.PRODUCT_COUNT));
        long userId = ((User) session.getAttribute(Constant.USER)).getId();
        List<Long> productInCart = basketDao.getProductsIdInBasket(userId);
        boolean alreadyInCart = false;
        for (Long product : productInCart) {
            if (product.equals(productId)) {
                alreadyInCart = true;
                break;
            }
        }
        if (!alreadyInCart) {
            Basket basket = new Basket();
            basket.setUserId(userId);
            basket.setProductId(productId);
            basket.setCount(productCount);
            basketDao.addProductToBasket(basket);
            response.sendRedirect(ConstantPageNamesJSPAndAction.MY_BASKET);
        } else {
            request.setAttribute(Constant.ERROR, Constant.ERROR_PRODUCT_ALREADY_IN_BASKET);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.HOME_SERVICE);
            dispatcher.forward(request, response);
        }
    }
}
