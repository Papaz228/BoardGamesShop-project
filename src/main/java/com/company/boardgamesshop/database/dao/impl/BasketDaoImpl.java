package com.company.boardgamesshop.database.dao.impl;
import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.entity.Basket;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.database.dao.interfaces.BasketDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class BasketDaoImpl implements BasketDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT_INTO_CART = "INSERT INTO basket (product_id, user_id, count) VALUES(?, ?, ?)";
    private static final String GET_PRODUCTS_FROM_CART_BY_USER_ID = "SELECT * FROM basket WHERE user_id = ?";
    private static final String DELETE_PRODUCT_FROM_CART = "DELETE FROM basket WHERE product_id = ? AND user_id = ?";
    private static final String GET_ALL_FROM_CART = "SELECT * FROM basket WHERE product_id = ?";
    private static final String DELETE_PRODUCTS_FROM_CART_BY_USER = "DELETE FROM basket WHERE user_id=?";
    private static final String UPDATE_PRODUCT_COUNT_IN_BASKET_BY_USER_AND_PRODUCT_ID="UPDATE basket SET count = ? WHERE user_id = ? AND product_id = ?";
    private static final String GET_COUNT_FROM_BASKET_BY_USER_ID="SELECT count FROM basket WHERE user_id=? AND product_id=?";
    @Override

    public void addProductToBasket(Basket basket) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCT_INTO_CART)){
            preparedStatement.setLong(1, basket.getProductId());
            preparedStatement.setLong(2, basket.getUserId());
            preparedStatement.setInt(3, basket.getCount());
            preparedStatement.executeUpdate();
        }
          catch (Exception e) {
              LOGGER.error(e);
            }
        finally {
            connectionPool.returnConnection(con);
        }}
    @Override
    public List<Long> getProductsIdInBasket(Long userId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con=connectionPool.getConnection();
        List<Long> productsIds = null;
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_PRODUCTS_FROM_CART_BY_USER_ID)){
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
            connectionPool.returnConnection(con);
        }
        return productsIds;
    }
    @Override
    public Integer countOfBasketByUserIdAndProductId(Long userId,Long productId){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con=connectionPool.getConnection();
        int count=0;
        try(PreparedStatement preparedStatement=con.prepareStatement(GET_COUNT_FROM_BASKET_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                count=resultSet.getInt(Constant.COUNT_TABLE);
            }
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.returnConnection(con);
        }
        return count;
    }
    @Override
    public void deleteProductInBasket(Long productId, Long userId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con=connectionPool.getConnection();
            try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_PRODUCT_FROM_CART)) {
                preparedStatement.setLong(1, productId);
                preparedStatement.setLong(2, userId);
                 preparedStatement.executeUpdate();
            } catch (Exception e) {
                LOGGER.error(e);
            } finally {
                connectionPool.returnConnection(con);
            }
        }
    @Override
    public List<Basket> getAllFromBasket(Long productId) {
        List<Basket> baskets =new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getInstance();
        Connection con=connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_FROM_CART)){
            preparedStatement.setLong(1,productId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Basket basket = new Basket();
                basket.setId(rs.getLong(Constant.ID));
                basket.setProductId(rs.getLong(Constant.PRODUCT_ID_TABLE));
                basket.setUserId(rs.getLong(Constant.USER_ID_TABLE));
                basket.setCount(rs.getInt(Constant.COUNT_TABLE));
                baskets.add(basket);
            }
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.returnConnection(con);
        }
        return baskets;
    }
    @Override
    public void deleteProductFromBasketByUserId(Long userId) {
        ConnectionPool connectionPool=ConnectionPool.getInstance();
        Connection con=connectionPool.getConnection();
        try( PreparedStatement preparedStatement = con.prepareStatement(DELETE_PRODUCTS_FROM_CART_BY_USER)){
            preparedStatement.setLong(1,userId);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.returnConnection(con);
        }
    }
    @Override
    public void updateProductCountInBasketByUserIdAndProductId(Long userId, Long productId, Integer count) {
        ConnectionPool connectionPool=ConnectionPool.getInstance();
        Connection con=connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_PRODUCT_COUNT_IN_BASKET_BY_USER_AND_PRODUCT_ID)) {
            preparedStatement.setLong(1, count);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, productId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.returnConnection(con);
        }
    }
}
