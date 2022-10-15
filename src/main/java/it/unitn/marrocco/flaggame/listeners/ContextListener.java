package it.unitn.marrocco.flaggame.listeners;

import it.unitn.marrocco.flaggame.Admin;
import it.unitn.marrocco.flaggame.Login;
import it.unitn.marrocco.flaggame.beans.UserCredentials;

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
    public void startNewServer(List<UserCredentials> userCredentials, String error_msg) {
        System.out.println(error_msg + ". Inserting standard users...");
        userCredentials.add(new UserCredentials(Admin.ADMIN_USERNAME, "nimda"));
        userCredentials.add(new UserCredentials("simone", "simone"));
    }
    
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        System.out.println("Server started");
        List<UserCredentials> userCredentials = new ArrayList<>();

        try {
            FileInputStream f = new FileInputStream(FILENAME);
            ObjectInputStream o = new ObjectInputStream(f);

            // Read objects
            while (f.available() > 0) {
                UserCredentials user = (UserCredentials) o.readObject();
                userCredentials.add(user);
            }

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            startNewServer(userCredentials, "File not found");
        } catch (IOException e) {
            startNewServer(userCredentials, "Error initializing stream");
        } catch (ClassNotFoundException e) {
            startNewServer(userCredentials, "File has not correct format");
        }
        System.out.println("Users:\n" + userCredentials);

        synchronized (contextEvent.getServletContext()) {
            ServletContext context = contextEvent.getServletContext();
            context.setAttribute("users", userCredentials);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        System.out.println("Server ended");

        List<UserCredentials> userCredentials;
        synchronized (contextEvent.getServletContext()) {
            ServletContext context = contextEvent.getServletContext();
            userCredentials = Login.getUsersFromContext(context);
        }
        System.out.println("Users:\n" + userCredentials);

        try {
            FileOutputStream f = new FileOutputStream(FILENAME);
            ObjectOutputStream o = new ObjectOutputStream(f);

            for (UserCredentials user : userCredentials) {
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
