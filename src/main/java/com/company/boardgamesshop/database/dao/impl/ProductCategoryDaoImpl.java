package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
import com.company.boardgamesshop.entity.ProductCategory;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoImpl implements ProductCategoryDao {
    private static final String INSERT_PRODUCT_CATEGORY = "INSERT INTO product_category (local_id, category_name) VALUES(?,?)";
    private static final String UPDATE_PRODUCT_CATEGORY = "UPDATE product_category SET category_name = ?, local_id = ? WHERE id = ?";
    private static final String GET_ALL_PRODUCT_CATEGORIES = "SELECT * FROM product_category WHERE local_id = ?";
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    @Override
    public List<ProductCategory> getAllProductCategoriesByLocalId(Long localId) {
        List<ProductCategory> productCategories = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_PRODUCT_CATEGORIES)) {
            preparedStatement.setLong(1, localId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(rs.getLong(Constant.ID));
                productCategory.setCategoryName(rs.getString(Constant.PRODUCT_CATEGORY_NAME_TABLE));
                productCategory.setLocalId(rs.getLong(Constant.LOCAL_ID_TABLE));
                productCategories.add(productCategory);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return productCategories;
    }

    @Override
    public void createAll(List<ProductCategory> productCategories) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try {
            for (ProductCategory productCategory : productCategories) {
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCT_CATEGORY);
                preparedStatement.setLong(1, productCategory.getLocalId());
                preparedStatement.setString(2, productCategory.getCategoryName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public void updateProductCategory(ProductCategory productCategory) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_PRODUCT_CATEGORY)) {
            preparedStatement.setString(1, productCategory.getCategoryName());
            preparedStatement.setLong(2, productCategory.getLocalId());
            preparedStatement.setLong(3, productCategory.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
    }
}

