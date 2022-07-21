package com.company.boardgamesshop.database.dao.impl;
import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.LocalDao;
import com.company.boardgamesshop.entity.Local;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class LocalDaoImpl implements LocalDao {
    private static final String GET_ALL_LOCALS = "SELECT * FROM \"BoardGames\".\"Local\"";
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    @Override
    public List<Local> getAllLocal() {
        List<Local> locals =new ArrayList<>();
        ConnectionPool connectionPool=ConnectionPool.getConnPool();
        Connection con=connectionPool.getConn();
        try(PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_LOCALS)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Local local = new Local();
                local.setId(rs.getLong(Constant.ID));
                local.setName(rs.getString("name"));
                local.setShortName(rs.getString("short_name"));
                locals.add(local);
            }
        }catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.freeConn(con);
        }
        return locals;
    }}
