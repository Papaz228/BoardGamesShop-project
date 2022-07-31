package com.company.boardgamesshop.database.dao.impl;

import com.company.boardgamesshop.database.connection.ConnectionPool;
import com.company.boardgamesshop.database.dao.interfaces.LanguageDao;
import com.company.boardgamesshop.entity.Language;
import com.company.boardgamesshop.util.constants.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LanguageDaoImpl implements LanguageDao {
    private static final String GET_ALL_LOCALS = "SELECT * FROM local";
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    @Override
    public List<Language> getAllLanguages() {
        List<Language> languages = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_LOCALS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Language language = new Language();
                language.setId(rs.getLong(Constant.ID));
                language.setName(rs.getString(Constant.NAME));
                language.setShortName(rs.getString(Constant.SHORT_NAME_TABLE));
                languages.add(language);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(con);
        }
        return languages;
    }
}
