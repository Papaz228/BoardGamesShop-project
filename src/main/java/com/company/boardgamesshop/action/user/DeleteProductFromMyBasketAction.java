package com.company.boardgamesshop.action.user;


import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.BasketDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.BasketDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DeleteProductFromMyBasketAction implements Action {
    BasketDao basketDao = new BasketDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {

        HttpSession session = request.getSession();
        long userId = ((User)session.getAttribute(Constant.USER)).getId();

        long productId = Long.parseLong(request.getParameter(Constant.PRODUCT_ID));
        basketDao.deleteProductInBasket(productId,userId);

        response.sendRedirect(ConstantPageNamesJSPAndAction.MY_BASKET);


    }
}
