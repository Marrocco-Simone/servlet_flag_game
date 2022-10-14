package it.unitn.marrocco.flaggame.beans;

import java.io.Serializable;

public class UserSession implements Comparable<UserSession>, Serializable {
    private String username;
    private int points;

    public UserSession(String username, int points) {
        setUsername(username);
        setPoints(points);
    }

    public int getPoints () {
        return this.points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public String getUsername () {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(UserSession other_user){
        return other_user.points - this.points;
    }
}
