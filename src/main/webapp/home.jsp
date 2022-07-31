<%@ include file="header.jsp" %>
<div class="modal fade lug" id="myModal" role="dialog">
    <div class="modal-dialog">
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
        <li><a href="howitworks.html"><fmt:message key="label.home"/></a></li>
        <li><a href="#"><fmt:message key="label.home2"/></a></li>
    </ul>
</div>
<div class="page-content-product">
    <div class="main-product">
        <div class="container">
            <div class="row clearfix">
                <div class="container">
                    <div class="find-box">
                        <div class="col-sm-3">
                            <div class="form-sh"></div>
                        </div>
                    </div>
                </div>
                <div class="find-box">
                    <h1><fmt:message key="label.home"/></h1>
                    <h4><fmt:message key="label.home2"/></h4>
                </div>
            </div>
            <form id="contact" action="home" method="post">
                <select name="productCategoryId" required>
                    Select country for product
                    <c:forEach items="${productCategories}" var="productCategory">
                        <option value="${productCategory.getId()}"
                                <c:if test="${productCategory.getId() eq selectedCatId}">selected="selected"</c:if>
                        >
                                ${productCategory.getCategoryName()}
                        </option>
                    </c:forEach>
                </select>
                <button class="btn btn-danger" data-submit="...Sending"><fmt:message
                        key="button.showProductsByCategory"/></button>
            </form>
            <div class="row clearfix">
                <c:forEach var="product" items="${products }">
                    <div class="col-lg-3 col-sm-6 col-md-3">
                        <a href="product?id=${product.getId() }">
                            <div class="box-img">
                                <h4>${product.getName()}</h4>
                                <img src="${product.getPhotoUrl()}" alt=""/>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

