package com.company.boardgamesshop.action.admin;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.database.dao.impl.LocalDaoImpl;
import com.company.boardgamesshop.database.dao.impl.ProductCategoryDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.LocalDao;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
import com.company.boardgamesshop.entity.Local;
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
        LocalDao localDao=new LocalDaoImpl();
        List<Local> locals = null;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
            RequestDispatcher dispatcher;
            HttpSession session = request.getSession();
            ProductCategory productCategory=null;
            List<ProductCategory> productCategories=new ArrayList<>();
            String productCategoryName = request.getParameter("category_name");
            User currentUser = (User) session.getAttribute(Constant.USER);
            if (currentUser.isAdmin()) {
                if (productCategoryName != null) {
                    List<String> categoryNames = Arrays.asList(request.getParameterValues("category_name"));
                    for (int i=0;i<categoryNames.size();i++){
                        productCategory = new ProductCategory();
                        productCategory.setCategoryName(categoryNames.get(i));
                        productCategory.setLocalId(locals.get(i).getId());
                        productCategories.add(productCategory);
                    }
                    productCategoryDao.createAll(productCategories);
                    response.sendRedirect(ConstantPageNamesJSPAndAction.CREATE_PRODUCT_ACTION);
                } else {
                    locals =localDao.getAllLocal();
                    request.setAttribute("locals",locals);
                    dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.CREATE_NEW_CATEGORY_JSP);
                    dispatcher.forward(request, response);
                }
            }
            else {
                response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
            }
        }
    }


