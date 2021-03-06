package com.company.boardgamesshop.database.dao.impl;
import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.StatusDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class StatusDaoImpl implements StatusDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final String SELECT_ID_BY_STATUS_NAME = "SELECT id FROM status WHERE name = ?";
    @Override
    public Long getIdByStatusName(String statusName) {
        long id = 0;
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(SELECT_ID_BY_STATUS_NAME)){
            preparedStatement.setString(1, statusName);
            ResultSet rs = preparedStatement.executeQuery();
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
    }}
