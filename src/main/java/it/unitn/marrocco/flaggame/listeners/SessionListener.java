package it.unitn.marrocco.flaggame.listeners;

import it.unitn.marrocco.flaggame.beans.UserSession;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession old_session = se.getSession();
        String username = (String) old_session.getAttribute("username");
        System.out.println("Finished session for " + username);
        UserSession old_user_session = new UserSession(username, 0);

        synchronized (old_session.getServletContext()) {
            ServletContext context = old_session.getServletContext();
            Object loggedAttribute =  context.getAttribute("logged");
            @SuppressWarnings("unchecked")
            List<UserSession> logged = (ArrayList<UserSession>) loggedAttribute;
            logged.remove(old_user_session);
            System.out.println("new context: " + logged);
            System.out.println();
            context.setAttribute("logged", logged);
        }
    }
}
