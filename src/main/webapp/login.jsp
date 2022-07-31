<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <style>
        <%@include file="css/font-awesome.min.css"%>
    </style>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
            integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
            integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h3 style="color:red"><c:out value="${error}"/></h3>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <form class="form-signin" method="post" action="login">
                <h2 class="form-signin-heading setMargin"><fmt:message key="label.signIn"/></h2>
                <input type="email" class="form-control setMargin" placeholder="<fmt:message key="label.email"/>"
                       name="email" required autofocus>
                <input type="password" class="form-control setMargin" placeholder="<fmt:message key="label.password"/>"
                       name="password" required>
                <div class="setMargin">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit" name="button" value="Login">
                        <fmt:message key="button.login"/>
                    </button>
                </div>
            </form>
            <div class="setMargin">
                <a href="registration.jsp" class="btn btn-info" role="button" name="button"
                   value="Register"><fmt:message key="button.register"/></a>
            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
</body>
</html>
