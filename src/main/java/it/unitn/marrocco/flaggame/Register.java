package it.unitn.marrocco.flaggame;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "register", value = "/register")
public class Register extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        synchronized (this) {
            context = getServletContext();
        }
    }

    protected void sendRegisterForm(HttpServletRequest req, HttpServletResponse res, String error_msg) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        Main.addHtmlFragment(req, res, "fragments/html_file_start.html");
        if (error_msg != null && error_msg.length() != 0) {
            out.println("<p>"+ error_msg + "</p>");
        }
        Main.addHtmlFragment(req, res, "fragments/register_form.html");
        Main.addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
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

        List<User> users = Main.getUsersFromContext(context);

        // check the user does not already exist
        for (User user : users) {
            if (user.username.equals(username)) {
                sendRegisterForm(req, res, "User already exists");
                return;
            }
        }

        users.add(new User(username, password));
        synchronized (this) {
            context.setAttribute("users", users);
        }

        Login.setSession(req, username);

        res.sendRedirect("index.html");
    }
}
