<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Required meta tags -->

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>login</title>
    <!-- Bootstrap CSS -->
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
<%--    <c:if test="${requestScope.error != null} ">--%>
<%--&lt;%&ndash;&ndash;%&gt;--%>
<%--        <h3>dsa</h3>--%>
<%--        <h3 style="color:red"> <c:out value="${requestScope.error}" /> </h3>--%>


<%--    </c:if>--%>
    <h3 style="color:red"><c:out value="${error}" /> </h3>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <form class="form-signin" method="post" action="registration">
                <h2 class="form-signin-heading"><fmt:message key="label.registration"/></h2>




                <input name="first_name" id="FirstName" class="form-control setMargin" placeholder="<fmt:message key="label.first_name"/>"
                       required autofocus>



                <input name="last_name" id="LastName" class="form-control setMargin" placeholder="<fmt:message key="label.last_name"/>" required
                       autofocus>
                <input name="birthday" id="birthday" class="form-control setMargin" placeholder="<fmt:message key="label.birthday"/> yyyy-mm-dd" required
                       autofocus>
                <input name="phone_number" id="phone_number" class="form-control setMargin" placeholder="<fmt:message key="label.phone_number"/> 87075554433" required
                       autofocus>
                <input type="email" class="form-control setMargin" placeholder="<fmt:message key="label.email"/>" name="email" required
                       autofocus>


                <input name="password" type="password" id="inputPassword" class="form-control setMargin"
                       placeholder="<fmt:message key="label.password"/>" required>


                <div class="setMargin">
                    <button name="button" value="Register" class="btn btn-outline-success my-2 my-sm-0"
                            type="submit"><fmt:message key="button.register"/></button>
                </div>


            </form>
            <div class="setMargin">
                <a href="login" class="btn btn-info" role="button" name="button" value="Register"><fmt:message key="button.login"/></a>
            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
</body>

</html>
