package it.unitn.marrocco.flaggame;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        out.println("<p>List of cities</p>");
        out.println("<ol start='0'>");
        for(String capital: capitals) {
            out.println("<li>"+capital+"</li>");
        }
        out.println("</ol>");
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

    public int findCapitalId(String capital, ArrayList<String> capitals) {
        for(int i=0; i<capitals.size(); i++){
            if(capital.equals(capitals.get(i))) return i;
        }
        return -1;
    }

    public void printChosenCapitals(PrintWriter out, ArrayList<String> capitals, ArrayList<String> chosen_capitals) {
        out.println("<form action='game' method='POST'>");
        int max = capitals.size() -1;
        for(String capital: chosen_capitals){
            out.println("<div><label>");
            out.println("<img src='flags/"+capital+".png' width='150' height='100' />");
            out.println("<input name='"+findCapitalId(capital, capitals)+"' type='number' required min='0' max='"+max+"'/>");
            out.println("</label></div>");
        }
        out.println("<button type='submit'>Submit Responses</button>");
        out.println("</form>");
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
        printChosenCapitals(out, capitals, chosen_capitals);
        Main.addHtmlFragment(req, res, "fragments/html_file_end.html");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        UserSession user = Main.getUserSession(req, res);
        if (user == null) return;

        Enumeration<String> param_names = req.getParameterNames();
        boolean guessed_all = true;
        while(param_names.hasMoreElements()){
            String param_name = param_names.nextElement();
            String param_value = req.getParameter(param_name);
            if(!param_value.equals(param_name)){
                guessed_all = false;
                break;
            }
        }
        if(guessed_all) {
            user.points += 3;
        }else {
            if(user.points > 0) user.points -= 1;
        }
        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            Object loggedAttribute =  context.getAttribute("logged");
            @SuppressWarnings("unchecked")
            List<UserSession> logged = (ArrayList<UserSession>) loggedAttribute;

            for (UserSession logged_user: logged) {
                if(logged_user.username.equals(user.username)) {
                    logged_user.points = user.points;
                }
            }

            context.setAttribute("logged", logged);
        }

        HttpSession session = req.getSession();
        session.setAttribute("points", user.points);
        res.sendRedirect("index.html");
    }
}
