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
    <link rel="stylesheet" href="styles.css"/>
</head>
<body class="box-body xx-large">
    <% if (error_msg != null && error_msg.length()>0) { %>
        <p><%=error_msg%></p>
    <% } %>

    <form action="register" method="POST">
        <div class="login-input">
            <label for="username">Username</label>
            <input 
                id="username" 
                name="username" 
                type="text" 
                required
            />
        </div>
        <div class="login-input">
            <label for="password">Password</label>
            <input 
                id="password" 
                name="password" 
                type="password" 
                required
            />
        </div>
        <div class="login-input">
            <label for="confirm_password">Confirm Password</label>
            <input 
                id="confirm_password" 
                name="confirm_password" 
                type="password" 
                required
            />
        </div>
        <button type="submit">Submit</button>
        <button type="reset">Reset</button>
    </form>
</body>
</html>
