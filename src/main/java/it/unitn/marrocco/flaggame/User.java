package it.unitn.marrocco.flaggame;

public class User implements Comparable<User>{
    public String username;
    public String password;
    public int points;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
    }

    @Override
    public String toString(){
        return this.username+": "+this.points;
    }

    @Override
    public int compareTo(User other_user){
        return other_user.points - this.points;
    }
}
