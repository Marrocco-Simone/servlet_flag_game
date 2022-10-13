package it.unitn.marrocco.flaggame;

import java.io.*;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "main", value = "/index.html")
public class Main extends HttpServlet {
    public static void addHtmlFragment(HttpServletRequest req, HttpServletResponse res, String fileName) throws IOException, ServletException {
        req.getRequestDispatcher(fileName).include(req, res);
    }

    public static synchronized List<User> getUsersFromContext(ServletContext context) {
        Object usersAttribute =  context.getAttribute("users");
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) usersAttribute;
        return users;
    }

    public static void getFooter(PrintWriter out, String username) {
        out.println("<footer>" + username + "</footer>");
    }

    public static synchronized UserSession getUserSession(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null) {
            res.sendRedirect("login");
            return null;
        }
        int points = (int) session.getAttribute("points");
        return new UserSession(username, points);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        UserSession user = getUserSession(req, res);
        if (user == null) return;
        if (user.username.equals("admin")) {
            res.sendRedirect("admin");
            return;
        }

        PrintWriter out = res.getWriter();
        addHtmlFragment(req, res, "fragments/html_file_start.html");
        getFooter(out, user.username);

        out.println("<p>Points: " + user.points + "</p>");
        out.println("<form action='game'><button>Play</button></form>");

        addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
    }
}