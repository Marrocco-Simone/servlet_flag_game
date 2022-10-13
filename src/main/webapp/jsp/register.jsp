<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    String error_msg = (String) request.getAttribute("error_msg");
%>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <% if (error_msg != null && error_msg.length()>0) { %>
        <p><%=error_msg%></p>
    <% } %>

    <form action="register" method="POST">
        <div>
            <label>
                Username
                <input name="username" type="text"/>
            </label>
        </div>
        <div>
            <label>
                Password
                <input name="password" type="password"/>
            </label>
        </div>
        <div>
            <label>
                Confirm Password
                <input name="confirm_password" type="password"/>
            </label>
        </div>
        <button type="submit">Submit</button>
        <button type="reset">Reset</button>
    </form>
</body>
</html>
