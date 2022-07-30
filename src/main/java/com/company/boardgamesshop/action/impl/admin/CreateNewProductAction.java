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

import static com.company.boardgamesshop.validator.Validator.*;
import static com.company.boardgamesshop.validator.Validator.validateDigitWithRegex;

public class CreateNewProductAction implements Action {
    ProductDao productDao = new ProductDaoImpl();
    ProductFactory productFactory=ProductFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        String productName = request.getParameter("product_name");
        User currentUser = (User) session.getAttribute(Constant.USER);
        CountryDao countryDao=new CountryDaoImpl();
        ProductCategoryDao productCategoryDao=new ProductCategoryDaoImpl();
        boolean checkErrors=true;
        if(!currentUser.isAdmin()) {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
            checkErrors=false;
        }
        else if(productName == null) {
            checkErrors=false;
        }
        else if(validateNameWithRegex(request.getParameter("product_name"))){
                    request.setAttribute(Constant.ERROR, "Name format error");
                    checkErrors=false;
        }
        else if(!validateDigitWithRegex(request.getParameter("cost"))){
                    request.setAttribute(Constant.ERROR, "Cost format error");
                    checkErrors=false;

        }
        else if(!validateDigitWithRegex(request.getParameter("count"))){
                    request.setAttribute(Constant.ERROR, "Count format error");
                    checkErrors=false;
        }
        Long localId=(Long)session.getAttribute("localId");
        List<Country> countries = countryDao.getAllCountriesByLocalId(localId);
        request.setAttribute("countries",countries);
        List<ProductCategory> productCategories= productCategoryDao.getAllProductCategoriesByLocalId(localId);
        request.setAttribute("productCategories", productCategories);
        if(checkErrors){
            Product newProduct = productFactory.fillProduct(request);
            productDao.createProduct(newProduct);
            response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
        }
        else {
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.CREATE_PRODUCT_JSP);
            dispatcher.forward(request, response);
        }
    }
}

