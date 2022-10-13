package it.unitn.marrocco.flaggame;

import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return this.username + " -> " + this.password;
    }
}
