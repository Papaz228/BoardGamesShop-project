<%@ include file="header.jsp" %>
<div class="product-page-main">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="prod-page-title">
                    <h2><c:out value="${product.getName()}"/></h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm-8">
                <div class="md-prod-page">
                    <div class="md-prod-page-in">
                        <div class="page-preview">
                            <div class="preview">
                                <div class="preview-pic tab-content">
                                    <div class="tab-pane active" id="pic-1"><img src="${product.getPhotoUrl()}"
                                                                                 width="50px" height="100px" alt="#"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="description-box">
                        <div class="dex-a">
                            <h4><fmt:message key="label.descriptionProduct"/></h4>
                            <p><c:out value="${product.getDescription()}"/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3 col-sm-12">
                <div class="price-box-right">
                    <c:if test="${sessionScope.user.isAdmin() == true}">
                        <form action="updateProduct" method="post">
                            <input type="hidden" name="productId" value="<c:out value="${product.getId()}"/>"/>
                            <button class="btn btn-danger btn-lg"><fmt:message key="button.updateProduct"/></button>
                        </form>
                        <br>
                        <c:if test="${product.isActive() == false}">
                            <form action="deactivateProduct" method="post">
                                <input type="hidden" name="productId" value="<c:out value="${product.getId()}"/>"/>
                                <input type="hidden" name="isActive" value="1"/>
                                <td scope="col">
                                    <button class="btn btn-danger">activate product</button>
                                </td>
                            </form>
                        </c:if>
                        <c:if test="${product.isActive() == true}">
                            <form action="deactivateProduct" method="post">
                                <input type="hidden" name="productId" value="<c:out value="${product.getId()}"/>"/>
                                <input type="hidden" name="isActive" value="0"/>
                                <td scope="col">
                                    <button class="btn btn-danger">deactivate product</button>
                                </td>
                            </form>
                        </c:if>
                    </c:if>
                    <br>
                    <h4><fmt:message key="label.cost"/></h4>
                    <h3>$<c:out value="${product.getCost()}"/></h3>
                    <c:if test="${sessionScope.user.isAdmin() == false}">
                        <form action="addProductToCart" method="post">
                            <label>
                                <select name="productCount" required>
                                    Select country for product
                                    <c:forEach var="i" begin="1" end="${product.getCount()}" step="+1">
                                        <option value="${i}">${i}</option>
                                    </c:forEach>
                                </select>
                            </label>
                            <input type="hidden" name="productId" value="<c:out value="${product.getId()}"/>"/>
                            <button class=""><fmt:message key="button.addToCart"/></button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

