<%--
  Created by IntelliJ IDEA.
  User: simone
  Date: 13/10/22
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.unitn.marrocco.flaggame.Main" %>
<%@ page import="it.unitn.marrocco.flaggame.beans.UserSession" %>
<%@ page import="it.unitn.marrocco.flaggame.beans.Capitals" %>
<%
    Capitals cap = new Capitals();
    UserSession user = Main.getUserSession(request, response);
    if (user == null) return;
%>
<html>
<head>
    <title>Flag Game</title>
    <link rel="stylesheet" href="styles.css"/>
</head>
<body class="game-body">
    <footer><%=user.getUsername()%></footer>

    <div class="capitals-list">
        <p>List of cities</p>
        <ol start='0'>
            <% for(String capital: cap.getCapitals()) { %>
            <li><%=capital%></li>
            <% } %>
        </ol>
    </div>

    <form action='game' method='POST' class="game-form">
        <% for(String capital: cap.getChosenCapitals()){ %>
            <div class="game-input">
                <label for="<%=capital%>">
                    <img 
                        src='flags/<%=capital%>.png' 
                        width='150' 
                        height='100' 
                        alt='Refresh the page'
                    />
                </label>
                <input 
                    id="<%=capital%>"
                    name='<%=cap.findCapitalId(capital)%>'
                    type='number' 
                    required min='0' 
                    max='<%=cap.getNCapitals()%>'
                />
            </div>
        <% } %>
        <button type='submit'>Submit Responses</button>
    </form>
</body>
</html>
