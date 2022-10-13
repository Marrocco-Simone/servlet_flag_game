<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.unitn.marrocco.flaggame.UserSession" %>
<%@ page import="it.unitn.marrocco.flaggame.Main" %>
<%
    UserSession user = Main.getUserSession(request, response);
    if (user == null) return;
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <footer><%=user.username%></footer>
    <p><%=user.points%></p>

    <form action='game'>
        <button type='submit'>Play</button>
    </form>
</body>
</html>
