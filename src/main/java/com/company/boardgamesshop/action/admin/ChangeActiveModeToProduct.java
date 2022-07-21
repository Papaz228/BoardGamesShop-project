package com.company.boardgamesshop.action.admin;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Basket;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.BasketDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.BasketDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
public class ChangeActiveModeToProduct implements Action {
    ProductDao productDao = new ProductDaoImpl();
    BasketDao cartDao = new BasketDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute(Constant.USER);
        if(currentUser.isAdmin()) {
            long productId = Long.parseLong(request.getParameter(Constant.PRODUCT_ID));
            int isActiveInt = Integer.parseInt(request.getParameter(Constant.IS_ACTIVE));
            boolean isActive = isActiveInt == 1;
            List<Basket> carts = cartDao.getAllFromBasket(productId);
            for (Basket eachProductInCart : carts) {
                cartDao.deleteProductInBasket(productId, eachProductInCart.getUserId());
            }
            productDao.deactivateProduct(productId, isActive);
            response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
        }
        else{
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }
}
