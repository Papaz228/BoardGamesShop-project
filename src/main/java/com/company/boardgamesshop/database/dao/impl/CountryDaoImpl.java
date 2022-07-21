package com.company.boardgamesshop.database.dao.impl;
import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.CountryDao;
import com.company.boardgamesshop.entity.Country;
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
public class CountryDaoImpl implements CountryDao {
    private static final String GET_ALL_COUNTRIES = "SELECT * FROM \"BoardGames\".\"Country\" WHERE local_id = ?";
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    @Override
    public List<Country> getAllCountriesByLocalId(Long localId) throws SQLException, IOException {
        List<Country> countries =new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement pstmt = con.prepareStatement(GET_ALL_COUNTRIES)){
            pstmt.setLong(1, localId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getLong(Constant.ID));
                country.setName(rs.getString("name"));
                country.setLocalId(rs.getLong("local_id"));
                countries.add(country);
            }
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return countries;
    }}
