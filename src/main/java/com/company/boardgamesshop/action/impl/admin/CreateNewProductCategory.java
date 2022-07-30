package com.company.boardgamesshop.action.impl.admin;
import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.database.dao.impl.LanguageDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductCategoryDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.LanguageDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
import com.company.boardgamesshop.entity.Language;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class CreateNewProductCategory implements Action {
        ProductCategoryDao productCategoryDao=new ProductCategoryDaoImpl();
        LanguageDao languageDao =new LanguageDaoImpl();
        List<Language> languages;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
            RequestDispatcher dispatcher;
            HttpSession session = request.getSession();
            ProductCategory productCategory;
            User currentUser = (User) session.getAttribute(Constant.USER);
            if (currentUser.isAdmin()) {
            List<ProductCategory> productCategories=new ArrayList<>();
            String productCategoryName = request.getParameter(Constant.PRODUCT_CATEGORY_NAME_TABLE);
                if (productCategoryName == null) {
                    languages = languageDao.getAllLanguages();
                    request.setAttribute(Constant.LANGUAGES, languages);
                    dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.CREATE_NEW_CATEGORY_JSP);
                    dispatcher.forward(request, response);
                } else {
                    List<String> categoryNames = Arrays.asList(request.getParameterValues(Constant.PRODUCT_CATEGORY_NAME_TABLE));
                    for (int i=0;i<categoryNames.size();i++){
                        productCategory = new ProductCategory();
                        productCategory.setCategoryName(categoryNames.get(i));
                        productCategory.setLocalId(languages.get(i).getId());
                        productCategories.add(productCategory);
                    }
                    productCategoryDao.createAll(productCategories);
                    response.sendRedirect(ConstantPageNamesJSPAndAction.CREATE_PRODUCT_ACTION);
                }
            }
            else {
                response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
            }
        }
    }


