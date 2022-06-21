package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Product;
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

public class ProductDaoImpl  implements ProductDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private static final String GET_ALL_PRODUCTS = "SELECT * FROM \"BoardGames\".\"Product\"";
    private static final String GET_PRODUCT = "SELECT * FROM \"BoardGames\".\"Product\" WHERE id = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO \"BoardGames\".\"Product\"\n" +
            "(name, description, \"cost\", count, country_id, product_category_id, is_active, photo_url)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, true,?);";
    private static final String UPDATE_PRODUCT = "UPDATE \"BoardGames\".\"Product\"\n" +
            "SET \"name\"=?, description=?, \"cost\"=?, count=?, country_id=?, product_category_id=?, is_active=true, photo_url=?\n" +
            "WHERE id=?";
    private static final String DELETE_PRODUCT = "UPDATE \"BoardGames\".\"Product\" SET is_active= ? WHERE id = ? ";


    public Product getProductById(Long id) throws SQLException, IOException{
        Product product = null;
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement pstmt = con.prepareStatement(GET_PRODUCT);){
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getLong(Constant.ID));
                product.setName(rs.getString(Constant.NAME));
                product.setDescription(rs.getString(Constant.DESCRIPTION));
                product.setCost(rs.getInt("cost"));
                product.setCount(rs.getInt("count"));
                product.setCountryId(rs.getLong("country_Id"));
                product.setProductCategoryId(rs.getLong("product_Category_Id"));
                product.setActive(rs.getBoolean("is_active"));
                product.setPhotoUrl(rs.getString("photo_url"));
            }

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return product;
    }

    public List<Product> getAllProduct() throws SQLException, IOException{
        List<Product> products =new ArrayList<>();
        Product product = null;
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement pstmt = con.prepareStatement(GET_ALL_PRODUCTS);){

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getLong(Constant.ID));
                product.setName(rs.getString(Constant.NAME));
                product.setDescription(rs.getString(Constant.DESCRIPTION));
                product.setCost(rs.getInt("cost"));
                product.setCount(rs.getInt("count"));
                product.setCountryId(rs.getLong("country_Id"));
                product.setProductCategoryId(rs.getLong("product_category_Id"));
                product.setActive(rs.getBoolean("is_active"));
                product.setPhotoUrl(rs.getString("photo_url"));
                products.add(product);
            }

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException, IOException{
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        System.out.println(product);
        try(PreparedStatement preparedStatement = con.prepareStatement(UPDATE_PRODUCT);){
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getDescription());
            preparedStatement.setInt(3,product.getCost());
            preparedStatement.setInt(4,product.getCount());
            preparedStatement.setLong(5,product.getCountryId());
            preparedStatement.setLong(6,product.getProductCategoryId());
            preparedStatement.setString(7,product.getPhotoUrl());
            preparedStatement.setLong(8,product.getId());

            preparedStatement.executeUpdate();

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }

    public void createProduct(Product product) throws SQLException, IOException{
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement pstmt = con.prepareStatement(INSERT_PRODUCT);){
            pstmt.setString(1,product.getName());
            pstmt.setString(2,product.getDescription());
            pstmt.setInt(3,product.getCost());
            pstmt.setInt(4,product.getCount());
            pstmt.setLong(5,product.getCountryId());
            pstmt.setLong(6,product.getProductCategoryId());
            pstmt.setString(7,product.getPhotoUrl());
            pstmt.executeUpdate();

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }

//    public List<Product> getSearchedProducts(String productName) throws SQLException, IOException{
//        List<Product> products =new ArrayList<>();
//        ConnectionPool connectionPool=ConnectionPool.getConnPool();
//        Connection con=connectionPool.getConn();
//        try(PreparedStatement pstmt = con.prepareStatement(SELECT_SEARCHED_PRODUCT);){
//            String newWord = "'%";
//            newWord+=productName;
//            newWord+="%'";
//            System.out.println(newWord);
//            pstmt.setString(1, productName);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Product product = new Product();
//                product.setId(rs.getLong(ID));
//                product.setName(rs.getString(NAME));
//                product.setDescription(rs.getString(DESCRIPTION));
//                product.setPrice(rs.getLong(PRICE));
//                product.setImage_url(rs.getString(IMAGE_URL));
//                product.setUserId(rs.getLong(ID_USER));
//                products.add(product);
//            }
//            pstmt.close();
//            releaseConnection(con);
//
//        }catch (Exception e) {
//            try {
//                if (con != null)
//                    con.close();
//            } catch (SQLException e2) {
//            }
//            LOGGER.error(e);
//        }
//        return products;

//    }
    public void deactivateProduct(Long productId, boolean isActive)throws SQLException, IOException{
    ConnectionPool connectionPool=ConnectionPool.getConnPool();
    Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(DELETE_PRODUCT)){
            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setLong(2,productId);
            preparedStatement.executeUpdate();

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }
}
