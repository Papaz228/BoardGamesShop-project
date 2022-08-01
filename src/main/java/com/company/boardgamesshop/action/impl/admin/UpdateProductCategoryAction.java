package com.company.boardgamesshop.action.impl.admin;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.ProductCategoryDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
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

public class UpdateProductCategoryAction implements Action {
    ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.USER);
        Long localId = (Long) session.getAttribute(Constant.LOCAL_ID);
        String productCategoryName = request.getParameter(Constant.PRODUCT_CATEGORY_NAME_TABLE);
        boolean checkErrors = true;
        if (!currentUser.isAdmin()) {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
            checkErrors = false;
        } else if (productCategoryName == null) {
            List<ProductCategory> productCategories = productCategoryDao.getAllProductCategoriesByLocalId(localId);
            request.setAttribute(Constant.PRODUCT_CATEGORIES, productCategories);
            checkErrors = false;
        }
        if (checkErrors) {
            ProductCategory productCategory = new ProductCategory();
            Long productCategoryId = Long.parseLong(request.getParameter(Constant.PRODUCT_CATEGORY_ID));
            productCategory.setId(productCategoryId);
            productCategory.setCategoryName(productCategoryName);
            productCategory.setLocalId(localId);
            productCategoryDao.updateProductCategory(productCategory);
            response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
        } else {
            List<ProductCategory> productCategories = productCategoryDao.getAllProductCategoriesByLocalId(localId);
            request.setAttribute(Constant.PRODUCT_CATEGORIES, productCategories);
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.UPDATE_PRODUCT_CATEGORY_JSP);
            dispatcher.forward(request, response);
        }
    }
}
