package it.unitn.marrocco.flaggame;

public class UserSession implements Comparable<UserSession> {
    public String username;
    public int points;

    public UserSession(String username, int points) {
        this.username = username;
        this.points = points;
    }

    @Override
    public int compareTo(UserSession other_user){
        return other_user.points - this.points;
    }
}
