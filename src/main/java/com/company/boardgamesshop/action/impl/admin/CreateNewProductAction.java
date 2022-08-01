package com.company.boardgamesshop.action.impl.admin;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.CountryDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductCategoryDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.CountryDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Country;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.entity.ProductCategory;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.entity.factory.ProductFactory;
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

import static com.company.boardgamesshop.validator.Validator.validateDigitWithRegex;
import static com.company.boardgamesshop.validator.Validator.validateNameWithRegex;

public class CreateNewProductAction implements Action {
    ProductDao productDao = new ProductDaoImpl();
    ProductFactory productFactory = ProductFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        String productName = request.getParameter(Constant.PRODUCT_NAME);
        User currentUser = (User) session.getAttribute(Constant.USER);
        CountryDao countryDao = new CountryDaoImpl();
        ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl();
        boolean checkErrors = true;
        if (!currentUser.isAdmin()) {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
            checkErrors = false;
        } else if (productName == null) {
            checkErrors = false;
        } else if (validateNameWithRegex(request.getParameter(Constant.PRODUCT_NAME))) {
            request.setAttribute(Constant.ERROR, Constant.ERROR_FIRST_NAME_FORMAT);
            checkErrors = false;
        } else if (validateDigitWithRegex(request.getParameter(Constant.COST_TABLE))) {
            request.setAttribute(Constant.ERROR, Constant.ERROR_COST_FORMAT);
            checkErrors = false;

        } else if (validateDigitWithRegex(request.getParameter(Constant.COUNT_TABLE))) {
            request.setAttribute(Constant.ERROR, Constant.ERROR_COUNT_FORMAT);
            checkErrors = false;
        }
        if (checkErrors) {
            Product newProduct = productFactory.fillProduct(request);
            productDao.createProduct(newProduct);
            response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
        } else {
            Long localId = (Long) session.getAttribute(Constant.LOCAL_ID);
            List<Country> countries = countryDao.getAllCountriesByLocalId(localId);
            request.setAttribute(Constant.COUNTRIES, countries);
            List<ProductCategory> productCategories = productCategoryDao.getAllProductCategoriesByLocalId(localId);
            request.setAttribute(Constant.PRODUCT_CATEGORIES, productCategories);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.CREATE_PRODUCT_JSP);
            dispatcher.forward(request, response);
        }
    }
}

