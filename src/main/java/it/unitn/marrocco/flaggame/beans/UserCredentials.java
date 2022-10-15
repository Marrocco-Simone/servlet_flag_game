package it.unitn.marrocco.flaggame.beans;

import java.io.Serializable;

/** user credentials */
public class UserCredentials implements Serializable {
    private String username;
    private String password;

    public UserCredentials(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername () {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword () {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.username + " -> " + this.password;
    }
}
