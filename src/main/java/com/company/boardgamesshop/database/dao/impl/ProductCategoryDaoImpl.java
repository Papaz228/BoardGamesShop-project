package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.ProductCategoryDao;
import com.company.boardgamesshop.entity.ProductCategory;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoImpl implements ProductCategoryDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT_CATEGORY = "INSERT INTO \"BoardGames\".\"Product_category\"\n" +
            "(local_id, category_name)\n" +
            "VALUES(?,?)";
    private static final String GET_ALL_PRODUCT_CATEGORIES = "SELECT * FROM \"BoardGames\".\"Product_category\" WHERE local_id=?";
    @Override
    public void create(ProductCategory productCategory) throws SQLException, IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement pstmt = con.prepareStatement(INSERT_PRODUCT_CATEGORY);){
            pstmt.setString(1,productCategory.getCategoryName());
            pstmt.setLong(2,productCategory.getLocalId());
           pstmt.executeUpdate();
        }catch (SQLException e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }

    @Override
    public List<ProductCategory> getAllProductCategoriesByLocalId(Long localId) throws SQLException, IOException {
        List<ProductCategory> productCategories =new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement pstmt = con.prepareStatement(GET_ALL_PRODUCT_CATEGORIES)){
            pstmt.setLong(1, localId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(rs.getLong(Constant.ID));
                productCategory.setCategoryName(rs.getString("category_name"));
                productCategory.setLocalId(rs.getLong("local_id"));
                productCategories.add(productCategory);
            }

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return productCategories;

    }

    @Override
    public void createAll(List<ProductCategory> productCategories) throws SQLException, IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try{
            for(ProductCategory productCategory: productCategories) {
                PreparedStatement pstmt = con.prepareStatement(INSERT_PRODUCT_CATEGORY);
                pstmt.setLong(1, productCategory.getLocalId());
                pstmt.setString(2, productCategory.getCategoryName());

                pstmt.executeUpdate();
                pstmt.close();
            }
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }

    }
}

