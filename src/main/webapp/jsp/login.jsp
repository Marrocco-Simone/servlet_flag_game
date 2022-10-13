<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String error_msg = (String) request.getAttribute("error_msg");
%>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles.css"/>
</head>
<body>
    <% if (error_msg != null && error_msg.length()>0) { %>
        <p><%=error_msg%></p>
    <% } %>

    <form action="login" method="POST">
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
        <button type="submit">Submit</button>
        <button type="reset">Reset</button>
    </form>

    <a href='register'>Register New Account</a>
</body>
</html>
