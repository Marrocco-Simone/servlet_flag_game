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

        context = getServletContext();
        context.setAttribute("users", users);
    }

    public static void addHtmlFragment(HttpServletRequest req, HttpServletResponse res, String fileName) throws IOException, ServletException {
        req.getRequestDispatcher(fileName).include(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null) {
            res.sendRedirect("login");
            return;
        }

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) context.getAttribute("users");
        int points = 0;
        for (User user : users) {
            if (user.username.equals(username)) {
                points = user.points;
                break;
            }
        }

        PrintWriter out = res.getWriter();
        addHtmlFragment(req, res, "fragments/html_file_start.html");
        out.println("<footer>" + username + " (points: " + points + ")</footer>");

        /* for (User user : users) {
            out.println(user);
        } */

        out.println("Let's Play");

        addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
    }
}