package com.company.boardgamesshop.action.impl.common;
import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.ProductCategoryDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.entity.ProductCategory;
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
import java.util.Objects;

public class AddAllProductsToShopAction implements Action {
    private final ProductDao PRODUCT_DAO = new ProductDaoImpl();
    private final ProductCategoryDao PRODUCT_CATEGORY_DAO=new ProductCategoryDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.USER);
        if(currentUser!=null) {
            Long localId= (Long) session.getAttribute(Constant.LOCAL_ID);
            List<Product> products = PRODUCT_DAO.getAllProduct();
            List<ProductCategory> productCategories = PRODUCT_CATEGORY_DAO.getAllProductCategoriesByLocalId(localId);
            String productCategoryIdString=request.getParameter(Constant.PRODUCT_CATEGORY_ID);
            if (productCategoryIdString!=null) {
                Long productCategoryId=Long.parseLong(productCategoryIdString);
                products.removeIf(pr -> !pr.isActive() || pr.getCount() == 0 || !Objects.equals(pr.getProductCategoryId(), productCategoryId));
            }
            else if(!currentUser.isAdmin()){
                products.removeIf(pr -> !pr.isActive() || pr.getCount() == 0);
            }
            request.setAttribute(Constant.PRODUCT_CATEGORIES, productCategories);
            request.setAttribute(Constant.PRODUCTS, products);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.HOME_JSP);
            dispatcher.forward(request, response);
        }
        else {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }
}
