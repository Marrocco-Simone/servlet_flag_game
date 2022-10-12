package it.unitn.marrocco.flaggame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "main", value = "/index.html")
public class Main extends HttpServlet {

    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // https://stackoverflow.com/questions/26497241/getservletconfig-getservletcontext-returning-null-value
        // otherwise config is null
        super.init(config);

        List<User> users = new ArrayList<>();
        // admin account
        users.add(new User("admin", "admin"));

        synchronized (this) {
            context = getServletContext();
            context.setAttribute("users", users);
        }
    }

    public static void addHtmlFragment(HttpServletRequest req, HttpServletResponse res, String fileName) throws IOException, ServletException {
        req.getRequestDispatcher(fileName).include(req, res);
    }

    public static synchronized List<User> getUsersFromContext(ServletContext context) {
        Object usersAttribute;
        usersAttribute =  context.getAttribute("users");

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) usersAttribute;
        return users;
    }

    public void getFooter(PrintWriter out, String username) {
        out.println("<footer>");
        out.println("<span>" + username + "</span>");
        out.println("<span> <a href='leaderboard'>LeaderBoard</a> </span>");
        out.println("</footer>");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null) {
            res.sendRedirect("login");
            return;
        }
        int points = (int) session.getAttribute("points");

        PrintWriter out = res.getWriter();
        addHtmlFragment(req, res, "fragments/html_file_start.html");
        getFooter(out, username);

        out.println("<p>Points: " + points + "</p>");
        out.println("<a href='game'>Let's Play</a>");
        // out.println("<object data='flags/Rome.svg' width='150' height='100'> </object>");

        addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
    }
}