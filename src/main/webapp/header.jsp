<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Online Shop</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="css/font-awesome.min.css"%>
        <!--
        bootstrap css-- ><%--    //<link rel="stylesheet" href="/css/bootstrap.min.css">--%><%@include file="css/bootstrap.min.css"%>

        <!--
        animate css-- ><%--    <link rel="stylesheet" href="css/animate-wow.css">--%><%@include file="css/bootstrap.min.css"%>

        <!--
        main css-- ><%--    <link rel="stylesheet" href="css/style.css">--%><%@include file="css/style.css"%><%--    <link rel="stylesheet" href="css/bootstrap-select.min.css">--%><%@include file="css/bootstrap-select.min.css"%><%--    <link rel="stylesheet" href="css/slick.min.css">--%><%@include file="css/slick.min.css"%>

        <!--
        responsive css-- >

        <%--    <link rel="stylesheet" href="css/responsive.css">--%>
        <%@include file="css/responsive.css"%>
    </style>
</head>
<body>
<header id="header" class="top-head">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-4 col-sm-12 left-rs">
                    <div class="navbar-header">
                        <button type="button" id="top-menu" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#navbar" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a href="home" class="navbar-brand"><img src="images/logo_w.png" alt=""/></a>
                    </div>
                </div>
                <div class="col-md-8 col-sm-12">
                    <div class="right-nav">
                        <div class="login-sr">
                            <div class="login-signup">
                                <ul>
                                    <li><a href="login"><fmt:message key="button.login"/></a></li>
                                    <c:if test="${sessionScope.user != null}">
                                        <li><a href="myProfile"><fmt:message key="label.profile"/> </a></li>
                                        <li><a href="logout"><fmt:message key="button.logout"/></a></li>
                                        <li><a href="myBasket"><fmt:message key="button.myBasket"/></a></li>
                                        <li><a href="myOrders"><fmt:message key="button.myOrders"/></a></li>
                                        <form action="changeLanguage" method="get">
                                            <c:if test="${sessionScope.language == ru || sessionScope.localId==2}">
                                                <input type="hidden" name="language" value="en">
                                                <input type="hidden" name="localId" value="1">
                                            </c:if>
                                            <c:if test="${sessionScope.language == en || sessionScope.localId==1}">
                                                <input type="hidden" name="language" value="ru">
                                                <input type="hidden" name="localId" value="2">
                                            </c:if>
                                            <li>
                                                <input type="submit" class="btn btn-primary btn-sm"
                                                       value="Change language">
                                            </li>
                                        </form>
                                    </c:if>
                                    <h3 style="color:red"><c:out value="${error}"/></h3>
                                    <c:if test="${sessionScope.user.isAdmin() == true}">
                                        <li>
                                            <div class="form-sh"><a role="button" class="btn btn-lg"
                                                                    href="createProduct"><fmt:message
                                                    key="button.createProduct"/></a></div>
                                        </li>
                                        <li>
                                            <div class="form-sh"><a class="btn btn-lg" href="allUsers"><fmt:message
                                                    key="button.manageUsers"/></a></div>
                                        </li>
                                        <li>
                                            <div class="form-sh"><a class="btn btn-lg" href="ordersAdmin"><fmt:message
                                                    key="button.orders"/></a></div>
                                        </li>
                                        <li>
                                            <div class="form-sh"><a class="btn btn-lg"
                                                                    href="createNewProductCategory"><fmt:message
                                                    key="button.createProductCategory"/></a></div>
                                        </li>
                                        <li>
                                            <div class="form-sh"><a class="btn btn-lg"
                                                                    href="updateProductCategory"><fmt:message
                                                    key="label.updateProductCategory"/></a></div>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>