package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.CountryDao;
import com.company.boardgamesshop.entity.Country;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl implements CountryDao {
    private static final String GET_ALL_COUNTRIES = "SELECT * FROM country WHERE local_id = ?";
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    @Override
    public List<Country> getAllCountriesByLocalId(Long localId) {
        List<Country> countries = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_COUNTRIES)) {
            preparedStatement.setLong(1, localId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getLong(Constant.ID));
                country.setName(rs.getString(Constant.NAME));
                country.setLocalId(rs.getLong(Constant.LOCAL_ID_TABLE));
                countries.add(country);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return countries;
    }
}
