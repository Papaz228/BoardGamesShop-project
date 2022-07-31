package com.company.boardgamesshop.entity.factory;

import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.util.constants.Constant;

import javax.servlet.http.HttpServletRequest;

public class ProductFactory {
    private static ProductFactory instance = new ProductFactory();

    public static ProductFactory getInstance() {
        if (instance == null) {
            instance = new ProductFactory();
        }
        return instance;
    }

    public Product fillProduct(HttpServletRequest request) {
        Product product = new Product();
        product.setName(request.getParameter(Constant.PRODUCT_NAME));
        product.setDescription(request.getParameter(Constant.DESCRIPTION));
        product.setCost(Integer.parseInt(request.getParameter(Constant.COST_TABLE)));
        product.setCount(Integer.parseInt(request.getParameter(Constant.COUNT_TABLE)));
        product.setPhotoUrl(request.getParameter(Constant.PHOTO_URL_TABLE));
        product.setCountryId(Long.parseLong(request.getParameter(Constant.COUNTRY_ID_TABLE)));
        product.setProductCategoryId(Long.parseLong(request.getParameter(Constant.PRODUCT_CATEGORY_ID)));
        product.setActive(true);
        return product;
    }
}