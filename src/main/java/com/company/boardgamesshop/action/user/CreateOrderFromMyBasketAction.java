package com.company.boardgamesshop.action.user;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.database.dao.impl.*;
import com.company.boardgamesshop.database.dao.interfaces.*;
import com.company.boardgamesshop.entity.*;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
public class CreateOrderFromMyBasketAction implements Action {
    BasketDao basketDao = new BasketDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
    StatusDao statusDao = new StatusDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    OrderDetailDao orderItemDao = new OrderDetailDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        long userId = ((User)session.getAttribute(Constant.USER)).getId();
        Status status = new Status();
        Long localId=(Long) session.getAttribute("localId");
        status.setLocalId(localId);
        if(localId==1){
            status.setId(statusDao.getIdByStatusName("Pending"));
            status.setName("Pending");
        }
        else{
            status.setId(statusDao.getIdByStatusName("Выполняется"));
            status.setName("Выполняется");
        }
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();
        Timestamp dateTime = Timestamp.valueOf(now);
        order.setDateStart(dateTime);
        order.setUserId(userId);
        order.setStatusId(status.getId());
        List<Long> productIdsInCart = basketDao.getProductsIdInBasket(userId);
        Integer totalCost=Integer.parseInt(request.getParameter("totalCost"));
        order.setTotalCost(totalCost);
        orderDao.createOrder(order);
        Long orderId=orderDao.takeLastID();
        if(orderId==null||orderId==0) orderId= 1L;
        for(long productId : productIdsInCart) {
            OrderDetail orderDetail = new OrderDetail();
            Product product = productDao.getProductById(productId);
            Integer count = basketDao.countOfBasketByUserId(userId, product.getId());
            product.setCount(product.getCount() - count);
            productDao.updateProduct(product);
            product.setCount(count);
            orderDetail.setProductId(product.getId());
            orderDetail.setCost(product.getCost());
            orderDetail.setCount(product.getCount());
            orderDetail.setOrderId(orderId);
            orderItemDao.createOrderDetail(orderDetail);
        }
        basketDao.deleteProductFromBasketByUserId(userId);
        response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
    }
}
