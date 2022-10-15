package it.unitn.marrocco.flaggame;

import it.unitn.marrocco.flaggame.beans.UserCredentials;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "register", value = "/register")
public class Register extends HttpServlet {
    protected void sendRegisterForm(HttpServletRequest req, HttpServletResponse res, String error_msg) throws IOException, ServletException {
        req.setAttribute("error_msg", error_msg);
        req.getRequestDispatcher("jsp/register.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        sendRegisterForm(req, res, "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirm_password = req.getParameter("confirm_password");

        if(!password.equals(confirm_password)) {
            sendRegisterForm(req, res, "Passwords are not equals");
            return;
        }

        List<UserCredentials> userCredentials;
        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            userCredentials = Login.getUsersFromContext(context);
        }

        // check the user does not already exist
        for (UserCredentials user : userCredentials) {
            if (user.getUsername().equals(username)) {
                sendRegisterForm(req, res, "User already exists");
                return;
            }
        }

        userCredentials.add(new UserCredentials(username, password));
        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            context.setAttribute("users", userCredentials);
            Login.setSession(req, username, context);
        }

        res.sendRedirect(req.getContextPath());
    }
}
