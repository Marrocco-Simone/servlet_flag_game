package it.unitn.marrocco.flaggame;

import it.unitn.marrocco.flaggame.beans.User;
import it.unitn.marrocco.flaggame.beans.UserSession;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    protected void sendLoginForm(HttpServletRequest req, HttpServletResponse res, String error_msg) throws IOException, ServletException {
        req.setAttribute("error_msg", error_msg);
        req.getRequestDispatcher("jsp/login.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        sendLoginForm(req, res, "");
    }

    public static synchronized List<User> getUsersFromContext(ServletContext context) {
        Object usersAttribute =  context.getAttribute("users");
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) usersAttribute;
        return users;
    }

    /** create a new session and add it in the context, or modify the previous one */
    public static synchronized void setSession(HttpServletRequest req, String username, ServletContext context){
        System.out.println("started session for " + username);

        Object loggedAttribute =  context.getAttribute("logged");
        @SuppressWarnings("unchecked")
        List<UserSession> logged = (ArrayList<UserSession>) loggedAttribute;
        if (logged == null) logged = new ArrayList<>();

        HttpSession session = req.getSession();

        // if the user already had a session
        String old_username = (String) session.getAttribute("username");
        if (old_username != null) {
            System.out.println("old login to delete: " + old_username);
            UserSession old_session = new UserSession(old_username, 0);
            logged.remove(old_session);
        }

        session.setAttribute("username", username);
        session.setAttribute("points", 0);
        UserSession new_session = new UserSession(username, 0);

        logged.add(new_session);
        System.out.println("new context: " + logged);
        System.out.println();
        context.setAttribute("logged", logged);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        List<User> users;
        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            users = getUsersFromContext(context);
        }
        Iterator<User> iter = users.iterator();
        boolean found = false;
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.getUsername().equals(username)) {
                found = true;
                if (!user.getPassword().equals(password)) {
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

        res.sendRedirect(req.getContextPath());
    }
}
