package it.unitn.marrocco.flaggame;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "game", value = "/game")
public class Game extends HttpServlet {
    int CAPITALS_TO_GUESS = 3;

    public ArrayList<String> initCapitals(){
        ArrayList<String> capitals = new ArrayList<>();

        capitals.add("Athens");
        capitals.add("Beijing");
        capitals.add("Cairo");
        capitals.add("New_Delhi");
        capitals.add("Paris");
        capitals.add("Prague");
        capitals.add("Rome");
        capitals.add("Seoul");
        capitals.add("Tokyo");
        capitals.add("Vienna");

        Collections.shuffle(capitals);
        return capitals;
    }

    public void printCapitalsList(PrintWriter out, ArrayList<String> capitals) {
        out.println("<table>");
        out.println("<tr>");
            out.println("<th>Index</th>");
            out.println("<th>Capital</th>");
        out.println("</tr>");
        int rank = 1;
        for(String capital: capitals) {
            out.println("<tr>");
            out.println("<td>"+rank+"</td>");
            out.println("<td>"+capital+"</td>");
            out.println("</tr>");

            rank++;
        }
        out.println("</table>");
    }

    public ArrayList<String> getChosenCapitals(ArrayList<String> capitals) {
        ArrayList<String> chosen_capitals = new ArrayList<>();
        Random rand = new Random();

        while (chosen_capitals.size() < CAPITALS_TO_GUESS) {
            int i = rand.nextInt(capitals.size());
            String capital = capitals.get(i);
            if(!chosen_capitals.contains(capital)){
                chosen_capitals.add(capital);
            }
        }

        return chosen_capitals;
    }

    public void printChosenCapitals(PrintWriter out, ArrayList<String> chosen_capitals) {
        int counter = 0;
        for(String capital: chosen_capitals){
            out.println("<div>");
            out.println("<img src='flags/"+capital+".png' width='150' height='100'></img>");
            out.println("<label>Guess<input name='capital_"+counter+"' type='number'/></label>");
            out.println("</div>");

            counter++;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        UserSession user = Main.getUserSession(req, res);
        if (user == null) return;

        ArrayList<String> capitals = initCapitals();

        PrintWriter out = res.getWriter();
        Main.addHtmlFragment(req, res, "fragments/html_file_start.html");
        Main.getFooter(out, user.username);
        printCapitalsList(out, capitals);

        ArrayList<String> chosen_capitals = getChosenCapitals(capitals);
        printChosenCapitals(out, chosen_capitals);
        Main.addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
    }
}
