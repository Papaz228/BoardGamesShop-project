package com.company.boardgamesshop.action.impl.admin;
import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.entity.Country;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.entity.ProductCategory;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.entity.factory.ProductFactory;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.CountryDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductCategoryDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.CountryDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
public class UpdateProductAction implements Action {
    ProductDao productDao = new ProductDaoImpl();
    CountryDao countryDao=new CountryDaoImpl();
    ProductCategoryDao productCategoryDao=new ProductCategoryDaoImpl();
    ProductFactory productFactory=ProductFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.USER);
        String productName = request.getParameter(Constant.PRODUCT_NAME);
        if (currentUser.isAdmin()) {
            if (productName != null) {
                Product product = productFactory.fillProduct(request);
                product.setId(Long.parseLong(request.getParameter(Constant.PRODUCT_ID)));
                productDao.updateProduct(product);
                response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
            } else {
                Long localId=(Long)session.getAttribute(Constant.LOCAL_ID);
                List<Country> countries = countryDao.getAllCountriesByLocalId(localId);
                request.setAttribute(Constant.COUNTRIES,countries);
                List<ProductCategory> productCategories= productCategoryDao.getAllProductCategoriesByLocalId(localId);
                request.setAttribute(Constant.PRODUCT_CATEGORIES, productCategories);
                Product product = productDao.getProductById(Long.valueOf(request.getParameter(Constant.PRODUCT_ID)));
                request.setAttribute(Constant.PRODUCT, product);
                dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.UPDATE_PRODUCT_JSP);
                dispatcher.forward(request, response);
            }
        }
        else{
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }
}
