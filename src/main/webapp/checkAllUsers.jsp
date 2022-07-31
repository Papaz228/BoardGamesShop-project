<%@ include file="header.jsp" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.getFirstName()}</td>
                <td>${user.getLastName()}</td>
                <td>${user.getEmail()}</td>
                <c:if test="${user.isBanned() == false}">
                    <form action="banUser" method="post">
                        <input type="hidden" name="userId" value="<c:out value="${user.getId()}"/>"/>
                        <td scope="col">
                            <button class="btn btn-danger">ban this user</button>
                        </td>
                    </form>
                </c:if>
                <c:if test="${user.isBanned() == true}">
                    <form action="unbanUser" method="post">
                        <input type="hidden" name="userId" value="<c:out value="${user.getId()}"/>"/>
                        <td scope="col">
                            <button class="btn btn-success">unban this user</button>
                        </td>
                    </form>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="footer.jsp" %>
