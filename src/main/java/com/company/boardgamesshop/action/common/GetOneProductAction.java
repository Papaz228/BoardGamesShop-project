package com.company.boardgamesshop.action.common;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
public class GetOneProductAction implements Action {
    private final ProductDao PRODUCT_DAO = new ProductDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        long productId = Long.parseLong(request.getParameter(Constant.ID));
        Product product = PRODUCT_DAO.getProductById(productId);
        request.setAttribute(Constant.PRODUCT, product);
        dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.PRODUCT_DETAIL_JSP);
        dispatcher.forward(request, response);
    }
}
