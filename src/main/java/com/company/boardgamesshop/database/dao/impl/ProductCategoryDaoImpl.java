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
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT_CATEGORY = "INSERT INTO \"BoardGames\".\"Product_category\"\n" +
            "(local_id, category_name)\n" +
            "VALUES(?,?)";
    private static final String GET_ALL_PRODUCT_CATEGORIES = "SELECT * FROM \"BoardGames\".\"Product_category\" WHERE local_id=?";
    @Override
    public void create(ProductCategory productCategory)  {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCT_CATEGORY)){
            preparedStatement.setString(1,productCategory.getCategoryName());
            preparedStatement.setLong(2,productCategory.getLocalId());
           preparedStatement.executeUpdate();
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }}
    @Override
    public List<ProductCategory> getAllProductCategoriesByLocalId(Long localId) {
        List<ProductCategory> productCategories =new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_PRODUCT_CATEGORIES)){
            preparedStatement.setLong(1, localId);
            ResultSet rs = preparedStatement.executeQuery();
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
    public void createAll(List<ProductCategory> productCategories) {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try{
            for(ProductCategory productCategory: productCategories) {
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCT_CATEGORY);
                preparedStatement.setLong(1, productCategory.getLocalId());
                preparedStatement.setString(2, productCategory.getCategoryName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }}

