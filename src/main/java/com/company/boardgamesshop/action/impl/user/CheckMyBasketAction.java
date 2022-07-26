package com.company.boardgamesshop.action.impl.user;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.BasketDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.BasketDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.action.Action;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
public class CheckMyBasketAction implements Action {
    BasketDao basketDao = new BasketDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.USER);
        if (currentUser == null) {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
        Long userId= currentUser != null ? currentUser.getId() : null;
        List<Long> productIdsInCart = basketDao.getProductsIdInBasket(userId);
        if (productIdsInCart != null) {
                double sumOfPrice = 0;
                List<Product> productsInCart = new ArrayList<>();
                for (long productId : productIdsInCart) {
                    Product product = productDao.getProductById(productId);
                    if(product.getCount()==0 || !product.isActive()){
                        basketDao.deleteProductInBasket(productId, userId);
                        continue;
                    }
                    Integer count = basketDao.countOfBasketByUserIdAndProductId(userId, product.getId());
                    product.setCount(count);
                    productsInCart.add(product);
                    sumOfPrice += (double) product.getCost() * product.getCount();
                }
                request.setAttribute(Constant.PRODUCT_IDS_IN_CART, productsInCart);
                request.setAttribute(Constant.SUM_OF_PRICE, sumOfPrice);
                dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.BASKET_JSP);
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
            }
    }
}
