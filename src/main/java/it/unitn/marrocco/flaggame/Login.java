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
        synchronized (this) {
            context = getServletContext();
        }
    }

    protected void sendLoginForm(HttpServletRequest req, HttpServletResponse res, String error_msg) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        Main.addHtmlFragment(req, res, "fragments/html_file_start.html");
        if (error_msg != null && error_msg.length() != 0) {
            out.println("<p>"+ error_msg + "</p>");
        }
        Main.addHtmlFragment(req, res, "fragments/login_form.html");
        out.println("<a href='register'>Register New Account</a>");
        Main.addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        sendLoginForm(req, res, "");
    }

    public static synchronized void setSession(HttpServletRequest req, String username){
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        session.setAttribute("points", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        List<User> users = Main.getUsersFromContext(context);
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

        setSession(req, username);

        res.sendRedirect("index.html");
    }
}
