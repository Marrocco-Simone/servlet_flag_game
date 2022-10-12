package it.unitn.marrocco.flaggame;

public class User implements Comparable<User>{
    public String username;
    public String password;
    public int best_points;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.best_points = 0;
    }

    @Override
    public int compareTo(User other_user){
        return other_user.best_points - this.best_points;
    }
}
