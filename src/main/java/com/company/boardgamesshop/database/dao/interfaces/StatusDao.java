package com.company.boardgamesshop.database.dao.interfaces;

import com.company.boardgamesshop.entity.Status;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StatusDao  {
    Long getIdByStatusName(String statusName) throws SQLException, IOException;
    List<Status> getAllStatuses() throws SQLException, IOException;
    Status getStatusByOrderId(Long orderId) throws SQLException, IOException;
}
