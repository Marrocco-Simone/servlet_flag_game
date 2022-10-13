package it.unitn.marrocco.flaggame;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        System.out.println("Server started");

        List<User> users = new ArrayList<>();
        users.add(new User("admin", "admin"));
        users.add(new User("simone", "simone"));

        synchronized (contextEvent.getServletContext()) {
            ServletContext context = contextEvent.getServletContext();
            context.setAttribute("users", users);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        System.out.println("Server ended");

        List<User> users;
        synchronized (contextEvent.getServletContext()) {
            ServletContext context = contextEvent.getServletContext();
            users = Main.getUsersFromContext(context);
            System.out.println(users);
        }
    }
}
