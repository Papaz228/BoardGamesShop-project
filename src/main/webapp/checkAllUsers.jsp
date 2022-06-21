<%@ include file="header.jsp" %>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS  -->
<%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">--%>


<div class="container">
    <h1><fmt:message key="label.users"/></h1>
    <table class="table">
        <thead>
        <tr>

            <th scope="col"><fmt:message key="label.first_name"/></th>
            <th scope="col"><fmt:message key="label.last_name"/></th>
            <th scope="col"><fmt:message key="label.emailUser"/></th>
            <th scope="col"><fmt:message key="label.userActivity"/></th>

        </tr>
        </thead>
        <tbody>
<c:forEach var = "user" items="${users}">
        <tr>
            <td>${user.getFirstName()}</td>
            <td>${user.getLastName()}</td>
            <td>${user.getEmail()}</td>
            <c:if test="${user.isBanned() == false}">
                <form action="banUser" method="post">
                    <input type="hidden" name="userId" value="<c:out value="${user.getId()}"/>" />
                    <td scope="col">
                        <button class="btn btn-danger">ban this user</button></td>

                </form>
            </c:if>
            <c:if test="${user.isBanned() == true}">
                <form action="unbanUser" method="post">
                    <input type="hidden" name="userId" value="<c:out value="${user.getId()}"/>" />
                    <td scope="col">
                        <button class="btn btn-success">unban this user</button></td>

                </form>
            </c:if>
        </tr>
</c:forEach>
        </tbody>
    </table>
</div>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<%--<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>--%>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>--%>
<%--</body>--%>
<%--</html>--%>
<%@ include file="footer.jsp" %>
