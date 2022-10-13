<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.unitn.marrocco.flaggame.UserSession" %>
<%@ page import="java.util.ArrayList" %>
<%
    @SuppressWarnings("unchecked")
    ArrayList<UserSession> logged = (ArrayList<UserSession>) request.getAttribute("logged");
%>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>
    <table>
        <tr>
            <th>Rank</th>
            <th>Username</th>
            <th>Points</th>
        </tr>
        <% int rank = 1; %>
        <% for(UserSession user: logged) { %>
            <tr>
                <td><%=rank%></td>
                <td><%=user.username%></td>
                <td><%=user.points%></td>
            </tr>
        <% rank++; %>
        <% } %>
    </table>
</body>
</html>
