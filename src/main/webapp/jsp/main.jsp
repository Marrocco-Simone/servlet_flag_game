<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.unitn.marrocco.flaggame.beans.UserSession" %>
<%@ page import="it.unitn.marrocco.flaggame.Main" %>
<%
    UserSession userCredentials = Main.getUserSession(request, response);
    if (userCredentials == null) return;
%>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="styles.css"/>
</head>
<body class="box-body">
    <header><%=userCredentials.getUsername()%></header>
    <p>Points: <%=userCredentials.getPoints()%></p>

    <form action="game" class="play-form">
        <button type="submit">Play</button>
    </form>
</body>
</html>
