package it.unitn.marrocco.flaggame.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/** class containing capitals in random order and the three chosen for the user to guess */
public class Capitals implements Serializable {

    int CAPITALS_TO_GUESS = 3;
    int n_capitals;

    ArrayList<String> capitals;
    ArrayList<String> chosen_capitals;

    public Capitals() {
        capitals = new ArrayList<>();
        capitals.add("Athens");
        capitals.add("Beijing");
        capitals.add("Cairo");
        capitals.add("Madrid");
        capitals.add("Paris");
        capitals.add("Prague");
        capitals.add("Rome");
        capitals.add("Seoul");
        capitals.add("Tokyo");
        capitals.add("Vienna");
        Collections.shuffle(capitals);

        n_capitals = capitals.size();

        chosen_capitals = new ArrayList<>();
        Random rand = new Random();
        while (chosen_capitals.size() < CAPITALS_TO_GUESS) {
            int i = rand.nextInt(capitals.size());
            String capital = capitals.get(i);
            if(!chosen_capitals.contains(capital)){
                chosen_capitals.add(capital);
            }
        }
    }

    /** find the id of the given city in the capitals array. -1 if not found */
    public int findCapitalId(String capital) {
        for(int i=0; i<capitals.size(); i++){
            if(capital.equals(capitals.get(i))) return i;
        }
        return -1;
    }

    public int getNCapitals() {return n_capitals;}
    public ArrayList<String> getCapitals() {return capitals;}
    public ArrayList<String> getChosenCapitals() {return chosen_capitals;}
}
