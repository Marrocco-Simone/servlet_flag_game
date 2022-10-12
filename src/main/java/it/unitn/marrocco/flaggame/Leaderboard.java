package it.unitn.marrocco.flaggame;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "leaderboard", value = "/leaderboard")
public class Leaderboard extends HttpServlet {
    ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        synchronized (this) {
            context = getServletContext();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<User> users = Main.getUsersFromContext(context);
        Collections.sort(users);
        PrintWriter out = res.getWriter();

        Main.addHtmlFragment(req, res, "fragments/html_file_start.html");
        out.println("<table>");
        out.println("<tr>");
            out.println("<th>Rank</th>");
            out.println("<th>Username</th>");
            out.println("<th>Points</th>");
        out.println("</tr>");
        int rank = 1;
        for(User user: users) {
            out.println("<tr>");
                out.println("<td>"+rank+"</td>");
                out.println("<td>"+user.username+"</td>");
                out.println("<td>"+user.points+"</td>");
            out.println("</tr>");

            rank++;
        }
        out.println("</table>");
        Main.addHtmlFragment(req, res, "fragments/html_file_end.html");
    }
}
