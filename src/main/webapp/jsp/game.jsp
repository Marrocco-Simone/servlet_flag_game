<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unitn.marrocco.flaggame.Main" %>
<%@ page import="it.unitn.marrocco.flaggame.Game" %>
<%@ page import="it.unitn.marrocco.flaggame.UserSession" %>
<%
    @SuppressWarnings("unchecked")
    ArrayList<String> capitals = (ArrayList<String>) request.getAttribute("capitals");
    @SuppressWarnings("unchecked")
    ArrayList<String> chosen_capitals = (ArrayList<String>) request.getAttribute("chosen_capitals");
    // no params, meaning the user typed manually the url. Send him to the game page first
    if (capitals == null || chosen_capitals == null) {
        response.sendRedirect("game");
        return;
    }

    UserSession user = Main.getUserSession(request, response);
    if (user == null) return;

    int max = capitals.size() -1;
%>
<html>
<head>
    <title>Flag Game</title>
</head>
<body>
    <footer><%=user.username%></footer>

    <p>List of cities</p>
    <ol start='0'>
        <% for(String capital: capitals) { %>
        <li><%=capital%></li>
        <% } %>
    </ol>

    <form action='game' method='POST'>
        <% for(String capital: chosen_capitals){ %>
            <div><label>
                <img src='flags/<%=capital%>.png' width='150' height='100' alt='Refresh the page'/>
                <input name='<%=Game.findCapitalId(capital, capitals)%>' type='number' required min='0' max='<%=max%>'/>
            </label></div>
        <% } %>
        <button type='submit'>Submit Responses</button>
    </form>
</body>
</html>
