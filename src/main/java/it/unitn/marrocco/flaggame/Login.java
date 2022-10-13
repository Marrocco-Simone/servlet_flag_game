package it.unitn.marrocco.flaggame;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
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

    public static synchronized void setSession(HttpServletRequest req, String username, ServletContext context){
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        session.setAttribute("points", 0);

        Object loggedAttribute =  context.getAttribute("logged");
        @SuppressWarnings("unchecked")
        List<UserSession> logged = (ArrayList<UserSession>) loggedAttribute;
        if (logged == null) logged = new ArrayList<>();

        for (UserSession user: logged) {
            if(user.username.equals(username)) return;
        }

        logged.add(new UserSession(username, 0));
        context.setAttribute("logged", logged);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        List<User> users;
        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            users = Main.getUsersFromContext(context);
        }
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

        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            setSession(req, username, context);
        }

        res.sendRedirect("index.html");
    }
}
