package it.unitn.marrocco.flaggame;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = getServletContext();
    }

    public static void addHtmlFragment(HttpServletRequest req, HttpServletResponse res, String fileName) throws IOException, ServletException {
        req.getRequestDispatcher(fileName).include(req, res);
    }

    protected void sendLoginForm(HttpServletRequest req, HttpServletResponse res, String error_msg) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        addHtmlFragment(req, res, "fragments/html_file_start.html");
        if (error_msg != null && error_msg.length() != 0) {
            out.println("<p>"+ error_msg + "</p>");
        }
        addHtmlFragment(req, res, "fragments/login_form.html");
        out.println("<a href='register'>Register New Account</a>");
        addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        sendLoginForm(req, res, "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        List<User> users = (List<User>) context.getAttribute("users");
        Iterator<User> iter = users.iterator();
        boolean found = false;
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.username.equals(username)) {
                found = true;
                if (!user.password.equals(password)) {
                    // password not correct
                    sendLoginForm(req, res, "Password not correct");
                    return;
                }
                // account verified
                break;
            }
        }
        if (!found) {
            // username not found
            sendLoginForm(req, res, "Username not found");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("username", username);

        res.sendRedirect("main");
    }
}
