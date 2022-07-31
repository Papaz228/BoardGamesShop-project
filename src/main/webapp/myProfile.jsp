<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<div class="container">
    <h1 style="text-align: center"><fmt:message key="label.profile"/></h1>
    <div class="table-main table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="label.first_name"/></th>
                <th><fmt:message key="label.last_name"/></th>
                <th><fmt:message key="label.birthday"/></th>
                <th><fmt:message key="label.phone_number"/></th>
                <th><fmt:message key="label.email"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${user.getFirstName()}</td>
                <td>${user.getLastName()}</td>
                <td>${user.getBirthday()}</td>
                <td>${user.getPhoneNumber()}</td>
                <td>${user.getEmail()}</td>
                <form class="form-signin" method="post" action="myProfile">
                    <input type="password" class="form-control setMargin"
                           placeholder="<fmt:message key="label.password"/>" name="password" required>
                    <div class="setMargin">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit" name="button" value="">
                            <fmt:message key="label.changePassword"/>
                        </button>
                    </div>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="footer.jsp" %>
