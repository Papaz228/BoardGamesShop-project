<%@ include file="header.jsp" %>
<div class="cart-box-main">
    <div class="container">
        <div class="row">
            <h1 style="text-align: center"><fmt:message key="label.orders.allOrders"/></h1>
            <div class="col-lg-12">
                <div class="table-main table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th><fmt:message key="label.emailUser"/></th>
                            <th><fmt:message key="label.orders.orderedDate"/></th>
                            <th><fmt:message key="label.orders.orderStatus"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td class="thumbnail-img">
                                    <a href="orderDetail?orderId=${order.get(0)}">
                                            ${order.get(2)}
                                    </a>
                                </td>
                                <td class="name-pr">
                                    <a href="orderDetail?orderId=${order.get(0)}">
                                            ${order.get(1)}
                                    </a>
                                </td>
                                <td class="name-pr">
                                    <a href="orderDetail?orderId=${order.get(0)}">
                                            ${order.get(3)}
                                    </a>
                                </td>
                                <form action="changeStatus" method="post">
                                    <input type="hidden" name="orderId" value="<c:out value="${order.get(0)}"/>"/>
                                    <c:if test="${order.get(4)==1}">
                                        <input type="hidden" name="statusId" value=3/>
                                    </c:if>
                                    <c:if test="${order.get(4)==2}">
                                        <input type="hidden" name="statusId" value=6/>
                                    </c:if>
                                    <td>
                                        <button class="btn btn-danger">complete</button>
                                    </td>
                                </form>
                                <form action="changeStatus" method="post">
                                    <input type="hidden" name="orderId" value="<c:out value="${order.get(0)}"/>"/>
                                    <c:if test="${order.get(4)==1}">
                                        <input type="hidden" name="statusId" value=4/>
                                    </c:if>
                                    <c:if test="${order.get(4)==2}">
                                        <input type="hidden" name="statusId" value=7/>
                                    </c:if>
                                    <td>
                                        <button class="btn btn-danger">abort</button>
                                    </td>
                                </form>
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