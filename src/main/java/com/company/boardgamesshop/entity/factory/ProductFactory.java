package com.company.boardgamesshop.entity.factory;

import javax.servlet.http.HttpServletRequest;

import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.commons.codec.digest.DigestUtils;
public class ProductFactory {
    private static ProductFactory instance = new ProductFactory();
    public Product fillProduct(HttpServletRequest request) {
        Product product = new Product();
        product.setName(request.getParameter("product_name"));
        product.setDescription(request.getParameter("description"));
        product.setCost(Integer.parseInt(request.getParameter("cost")));
        product.setCount(Integer.parseInt(request.getParameter("count")));
        product.setPhotoUrl(request.getParameter("photo_url"));
        product.setCountryId(Long.parseLong(request.getParameter("countryId")));
        product.setProductCategoryId(Long.parseLong(request.getParameter("productCategoryId")));
        product.setActive(true);
        return product;
    }
    public static ProductFactory getInstance() {
        if (instance == null) {
            instance = new ProductFactory();
        }
        return instance;
    }
}