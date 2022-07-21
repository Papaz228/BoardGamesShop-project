package com.company.boardgamesshop.database.dao.interfaces;
import com.company.boardgamesshop.entity.OrderDetail;
import java.io.IOException;
import java.sql.SQLException;
public interface OrderDetailDao {
   void createOrderDetail(OrderDetail orderDetail)throws SQLException, IOException;
}