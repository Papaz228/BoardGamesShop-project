package com.company.boardgamesshop.action.impl.common;
import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
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
public class AddAllProductsToShopAction implements Action {
    private final ProductDao PRODUCT_DAO = new ProductDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.USER);
        List<Product> products = PRODUCT_DAO.getAllProduct();
        if(currentUser!=null && !currentUser.isAdmin()){  products.removeIf(pr -> !pr.isActive());}
        request.setAttribute(Constant.PRODUCTS, products);
        dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.HOME_JSP);
        dispatcher.forward(request, response);
    }
}
