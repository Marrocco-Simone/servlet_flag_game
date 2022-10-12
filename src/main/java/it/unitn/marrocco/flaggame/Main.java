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

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute("username") == null) {
            res.sendRedirect("login");
            return;
        }

        PrintWriter out = res.getWriter();
        out.println(session.getAttribute("username"));
        out.println(session.getId());

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) context.getAttribute("users");
        for (User user : users) {
            out.println(user);
        }

        out.close();
    }
}