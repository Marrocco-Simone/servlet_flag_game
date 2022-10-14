package it.unitn.marrocco.flaggame;

import it.unitn.marrocco.flaggame.beans.UserSession;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "game", value = "/game")
public class Game extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher("jsp/game.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        UserSession user = Main.getUserSession(req, res);
        if (user == null) return;

        Enumeration<String> param_names = req.getParameterNames();
        boolean guessed_all = true;
        while(param_names.hasMoreElements()){
            String param_name = param_names.nextElement();
            String param_value = req.getParameter(param_name);
            if(!param_value.equals(param_name)){
                guessed_all = false;
                break;
            }
        }
        if(guessed_all) {
            user.setPoints(user.getPoints()+3);
        }else {
            if(user.getPoints() > 0) user.setPoints(user.getPoints()-1);
        }
        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            Object loggedAttribute =  context.getAttribute("logged");
            @SuppressWarnings("unchecked")
            List<UserSession> logged = (ArrayList<UserSession>) loggedAttribute;

            for (UserSession logged_user: logged) {
                if(logged_user.getUsername().equals(user.getUsername())) {
                    logged_user.setPoints(user.getPoints());
                    break;
                }
            }

            context.setAttribute("logged", logged);
        }

        HttpSession session = req.getSession();
        session.setAttribute("points", user.getPoints());
        res.sendRedirect(req.getContextPath());
    }
}
