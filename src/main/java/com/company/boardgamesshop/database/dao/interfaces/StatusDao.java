package com.company.boardgamesshop.database.dao.interfaces;
import java.io.IOException;
import java.sql.SQLException;
public interface StatusDao {
    Long getIdByStatusName(String statusName) throws SQLException, IOException;
}
