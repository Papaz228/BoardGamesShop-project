package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.entity.Basket;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.util.constants.Constant;

import com.company.boardgamesshop.database.dao.interfaces.BasketDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketDaoImpl implements BasketDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private static final String INSERT_PRODUCT_INTO_CART = "INSERT INTO \"BoardGames\".\"Basket\" (product_id, user_id, count) VALUES(?, ?, ?)";
    private static final String GET_PRODUCTS_FROM_CART_BY_USER_ID = "SELECT * FROM \"BoardGames\".\"Basket\" WHERE user_id = ?";
    private static final String DELETE_PRODUCT_FROM_CART = "DELETE FROM \"BoardGames\".\"Basket\" WHERE product_id = ? AND user_id = ?";
    private static final String UPDATE_BASKET_AFTER ="UPDATE \"BoardGames\".\"Basket\" SET count = ?";
    private static final String DELETE_PRODUCT_BY_PRODUCT_ID_FROM_CART = "DELETE FROM Basket WHERE product_id = ? AND user_id = ?";
    private static final String GET_CART_ID_FROM_CART = "SELECT id FROM \"BoardGames\".\"Basket\" WHERE product_id = ? AND user_id = ?";
    private static final String GET_ALL_FROM_CART = "SELECT * FROM \"BoardGames\".\"Basket\" WHERE product_id = ?";
    private static final String DELETE_PRODUCTS_FROM_CART_BY_USER = "DELETE FROM \"BoardGames\".\"Basket\" WHERE user_id=?";
    private static final String GET_COUNT_FROM_BASKET_BY_USER_ID="SELECT count FROM \"BoardGames\".\"Basket\" WHERE user_id=? AND product_id=?";
    public void addProductToBasket(Basket basket)throws SQLException, IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement pstmt = con.prepareStatement(INSERT_PRODUCT_INTO_CART);){
            pstmt.setLong(1,basket.getProductId());
            pstmt.setLong(2,basket.getUserId());
            pstmt.setInt(3,basket.getCount());
            pstmt.executeUpdate();

        }
          catch (Exception e) {
              LOGGER.error(e);
            }
        finally {
            connectionPool.freeConn(con);
        }
        }


    public List<Long> getProductsIdInBasket(Long userId) throws SQLException, IOException{
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        List<Long> productsIds = null;
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_PRODUCTS_FROM_CART_BY_USER_ID);){
             productsIds = new ArrayList<>();
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long productId = resultSet.getLong(Constant.PRODUCT_ID_TABLE);
                productsIds.add(productId);
            }

        } catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return productsIds;
    }
    @Override
    public List<Product> getAllProductsInBasket(Long userId) throws SQLException, IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        List<Product> products = null;
        Product product=null;
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_PRODUCTS_FROM_CART_BY_USER_ID);){
            products = new ArrayList<>();
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               product=new Product();
               product.setId(resultSet.getLong("id"));
               product.setName(resultSet.getString("name"));
               product.setDescription(resultSet.getString("name"));
               product.setCost(resultSet.getInt("cost"));
               product.setCost(resultSet.getInt("count"));
               product.setCountryId(resultSet.getLong("country_id"));
               product.setProductCategoryId(resultSet.getLong("product_category_id"));
               product.setActive(resultSet.getBoolean("is_active"));
               products.add(product);
            }

        } catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return products;

    }

    public Integer countOfBasketByUserId(Long userId,Long productId){
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        int count=0;
        try(PreparedStatement preparedStatement=con.prepareStatement(GET_COUNT_FROM_BASKET_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                count=resultSet.getInt("count");
            }
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return count;
    }
    public void deleteProductInBasket(Long productId, Long userId) throws SQLException, IOException{
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
            try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_PRODUCT_FROM_CART);) {
                preparedStatement.setLong(1, productId);
                preparedStatement.setLong(2, userId);
                 preparedStatement.executeUpdate();



            } catch (Exception e) {
                LOGGER.error(e);
            } finally {
                connectionPool.freeConn(con);
            }
        }


    public Basket getBasket(Basket basket) throws SQLException,IOException {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        Basket newBasket=null;
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_CART_ID_FROM_CART)) {

            preparedStatement.setLong(1, basket.getProductId());
            preparedStatement.setLong(2, basket.getUserId());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                newBasket.setId(rs.getLong(Constant.ID));
            }
            }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return newBasket;
    }



    public List<Basket> getAllFromBasket(long productId) throws SQLException, IOException{
        List<Basket> baskets =new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try (PreparedStatement pstmt = con.prepareStatement(GET_ALL_FROM_CART);){
            pstmt.setLong(1,productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Basket basket = new Basket();
                basket.setId(rs.getLong(Constant.ID));
                basket.setProductId(rs.getLong(Constant.PRODUCT_ID_TABLE));
                basket.setUserId(rs.getLong(Constant.USER_ID_TABLE));
                basket.setCount(rs.getInt("count"));
                baskets.add(basket);
            }


        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return baskets;
    }



    @Override
    public void deleteProductFromBasketByUserId(Long userId)throws SQLException, IOException{
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement preparedStatement = con.prepareStatement(DELETE_PRODUCTS_FROM_CART_BY_USER);){
            preparedStatement.setLong(1,userId);
             preparedStatement.executeUpdate();

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }


}
