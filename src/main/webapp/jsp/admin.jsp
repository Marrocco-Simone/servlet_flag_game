<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.unitn.marrocco.flaggame.beans.UserSession" %>
<%@ page import="java.util.ArrayList" %>
<%
    @SuppressWarnings("unchecked")
    ArrayList<UserSession> logged = (ArrayList<UserSession>) request.getAttribute("logged");
%>
<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" href="styles.css"/>
</head>
<body class="box-body">
    <table class="admin-table">
        <tr>
            <th>Rank</th>
            <th>Username</th>
            <th>Points</th>
        </tr>
        <% int rank = 1; %>
        <% for(UserSession userCredentials: logged) { %>
            <tr>
                <td class="td-number"><%=rank%></td>
                <td class="td-username"><%=userCredentials.getUsername()%></td>
                <td class="td-number"><%=userCredentials.getPoints()%></td>
            </tr>
        <% rank++; %>
        <% } %>
    </table>
</body>
</html>
