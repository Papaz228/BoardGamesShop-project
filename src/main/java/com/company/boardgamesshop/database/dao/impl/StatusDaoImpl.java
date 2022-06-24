package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.StatusDao;
import com.company.boardgamesshop.entity.Status;
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

public class StatusDaoImpl implements StatusDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String SELECT_ID_BY_STATUS_NAME = "SELECT id FROM \"BoardGames\".\"Status\" WHERE name = ? ";

    @Override
    public Long getIdByStatusName(String statusName) throws SQLException, IOException {
        long id = 0;
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement pstmt = con.prepareStatement(SELECT_ID_BY_STATUS_NAME)){
            pstmt.setString(1, statusName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getLong("id");
            }

        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return id;
    }

}
