package com.company.boardgamesshop.database.dao.impl;
import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.ProductDao;
import com.company.boardgamesshop.entity.Product;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class ProductDaoImpl implements ProductDao {
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
    @Override
    public Product getProductById(Long id) {
        Product product = null;
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_PRODUCT)){
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
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
    @Override
    public List<Product> getAllProduct() {
        List<Product> products =new ArrayList<>();
        Product product;
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try( PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_PRODUCTS)){
            ResultSet rs = preparedStatement.executeQuery();
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
    @Override
    public void updateProduct(Product product) {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        System.out.println(product);
        try(PreparedStatement preparedStatement = con.prepareStatement(UPDATE_PRODUCT)){
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
    @Override
    public void createProduct(Product product) {
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(INSERT_PRODUCT)){
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getDescription());
            preparedStatement.setInt(3,product.getCost());
            preparedStatement.setInt(4,product.getCount());
            preparedStatement.setLong(5,product.getCountryId());
            preparedStatement.setLong(6,product.getProductCategoryId());
            preparedStatement.setString(7,product.getPhotoUrl());
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
    }
    @Override
    public void deactivateProduct(Long productId, boolean isActive) {
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
    }}
