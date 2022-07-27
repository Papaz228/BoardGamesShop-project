<%@ include file="header.jsp" %>
<div class="modal fade lug" id="myModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Change</h4>
            </div>

        </div>
    </div>
</div>
<div id="sidebar" class="top-nav">
    <ul id="sidebar-nav" class="sidebar-nav">
        <li><a href="#">Help</a></li>
        <li><a href="howitworks.html">How it works</a></li>
        <li><a href="#">chamb for Business</a></li>
    </ul>
</div>
<div class="page-content-product">
    <div class="main-product">
        <div class="container">

            <div class="row clearfix">
                <div class="container">
                    <div class="find-box">


                    </div>
                </div>
                <div class="find-box">
                    <h1><fmt:message key="label.cart.productInMyCart"/></h1>
                    <h4><fmt:message key="label.cart.sumOfProducts"/> $<c:out value="${sumOfPrice}"/></h4>
                    <c:if test="${sumOfPrice != 0}">
                        <form action="order" method="post" style="text-align: center">
                            <button  class="btn btn-success lg"><fmt:message key="button.orderProducts"/></button>
                        </form>
                    </c:if>

                </div>
            </div>

            <div class="row clearfix">
                <c:forEach var = "product" items="${productsInCart }">
                <div class="col-lg-3 col-sm-6 col-md-3">
                    <form action="updateProductCountInBasket" method="post">
                        <label>
                            <select name="changedProductCount" required>
                                Select country for product
                                <c:forEach var="i" begin="1" end="${productCount}" step="+1">
                                    <option value="${i}">${i}</option>
                                </c:forEach>
                            </select>
                        </label>
                        <input type="hidden" name="productId" value="<c:out value="${product.getId()}"/>" />
                        <button  class="btn btn-danger" data-submit="...Sending"><fmt:message key="button.changeCount"/></button>
                    </form>
                    <a href="product?id=${product.getId() }">
                        <div class="box-img">
                            <h4>${product.getName()}</h4>
                            <h4>${product.getCount()}</h4>
                            <img src="${product.getPhotoUrl()}" alt="" />

                            <form action="deleteProductFromCart" method="post">
                                <input type="hidden" name="productId" value="<c:out value="${product.getId()}"/>" />
                                <input type="hidden" name="productCount" value="<c:out value="${product.getCount()}"/>"/>

                                <button class="btn btn-danger" ><fmt:message key="button.deleteProductFromCart"/></button>
                            </form>
                        </div>
                    </a>
                </div>
                </c:forEach>

            </div>

        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>

