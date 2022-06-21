package com.company.boardgamesshop.action.admin;

import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.entity.Country;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.entity.ProductCategory;
import com.company.boardgamesshop.entity.User;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.USER);
        String productName = request.getParameter("product_name");

        if (currentUser.isAdmin()) {
            if (productName != null) {
                Product product = new Product();
                product.setName(request.getParameter("product_name"));
                product.setId(Long.parseLong(request.getParameter("productId")));
                product.setPhotoUrl(request.getParameter("photo_url"));
                product.setDescription(request.getParameter("description"));
                product.setCost(Integer.parseInt(request.getParameter("cost")));
                product.setCount(Integer.parseInt(request.getParameter("count")));
                product.setCountryId(Long.parseLong(request.getParameter("countryId")));
                product.setProductCategoryId(Long.parseLong(request.getParameter("productCategoryId")));
                product.setActive(true);
                productDao.updateProduct(product);
                response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
            } else {
                Long localId=(Long)session.getAttribute("localId");
                List<Country> countries = countryDao.getAllCountriesByLocalId(localId);
                request.setAttribute("countries",countries);
                List<ProductCategory> productCategories= productCategoryDao.getAllProductCategoriesByLocalId(localId);
                request.setAttribute("productCategories", productCategories);
                Product product = productDao.getProductById(Long.valueOf(request.getParameter(Constant.PRODUCT_ID)));
                request.setAttribute(Constant.PRODUCT, product);
                dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.UPDATE_PRODUCT_JSP);
                dispatcher.forward(request, response);
            }
        }
        else{
            response.sendRedirect("/login");
        }
    }
}
