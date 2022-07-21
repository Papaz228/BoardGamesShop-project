package com.company.boardgamesshop.database.dao.interfaces;
import com.company.boardgamesshop.entity.Local;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public interface LocalDao {
    List<Local> getAllLocal() throws SQLException, IOException;
}
