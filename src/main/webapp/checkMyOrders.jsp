<%@ include file="header.jsp" %>
<div class="cart-box-main">
    <div class="container">
        <div class="row">
            <h1 style="text-align: center"><fmt:message key="label.orders.myOrders"/></h1>
            <div class="col-lg-12">
                <div class="table-main table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th><fmt:message key="label.orders.orderedDate"/></th>
                            <th><fmt:message key="label.orders.orderStatus"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td class="thumbnail-img">
                                    <a href="orderDetail?orderId=${order.getId()}">
                                            ${order.getDateStart()}
                                    </a>
                                </td>
                                <td class="name-pr">
                                    <a href="orderDetail?orderId=${order.getId()}">
                                            ${order.getStatusName()}
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>