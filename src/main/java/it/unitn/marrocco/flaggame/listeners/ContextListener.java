package it.unitn.marrocco.flaggame.listeners;

import it.unitn.marrocco.flaggame.Admin;
import it.unitn.marrocco.flaggame.Main;
import it.unitn.marrocco.flaggame.beans.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * listener for servlet initialization and finalitation.
 * when the servlet is started, takes the user credentials data from FILENAME.
 * when the servlet is destroyed, saves the user credentials data in FILENAME.
 */
@WebListener
public class ContextListener implements ServletContextListener {
    String FILENAME = "Users.txt";

    /** if there is an error reading FILENAME, initialize the servlet as new with standard users */
    public void startNewServer(List<User> users, String error_msg) {
        System.out.println(error_msg + ". Inserting standard users...");
        users.add(new User(Admin.ADMIN_USERNAME, "admin"));
        users.add(new User("simone", "simone"));
    }
    
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        System.out.println("Server started");
        List<User> users = new ArrayList<>();

        try {
            FileInputStream f = new FileInputStream(FILENAME);
            ObjectInputStream o = new ObjectInputStream(f);

            // Read objects
            while (f.available() > 0) {
                User user = (User) o.readObject();
                users.add(user);
            }

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            startNewServer(users, "File not found");
        } catch (IOException e) {
            startNewServer(users, "Error initializing stream");
        } catch (ClassNotFoundException e) {
            startNewServer(users, "File has not correct format");
        }
        System.out.println("Users:\n" + users);

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
        }
        System.out.println("Users:\n" + users);

        try {
            FileOutputStream f = new FileOutputStream(FILENAME);
            ObjectOutputStream o = new ObjectOutputStream(f);

            for (User user: users) {
                o.writeObject(user);
            }

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }
}
