package hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GuessNumber extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_ATTEMPTS = 2;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer randomNumber = (Integer) session.getAttribute("randomNumber");
        Integer attempts = (Integer) session.getAttribute("attempts");
        Integer roundsWon = (Integer) session.getAttribute("roundsWon");
        
        if (randomNumber == null || attempts >= MAX_ATTEMPTS) {
            // New game or max attempts reached, generate a random number
            Random rand = new Random();
            randomNumber = rand.nextInt(100) + 1;
            attempts = 0;
            session.setAttribute("randomNumber", randomNumber);
            session.setAttribute("attempts", attempts);
        }
        
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Number Guessing Game</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"NewFile.css\">");
        out.println("</head><body>");

        out.println("<html><head><title>Number Guessing Game</title></head><body>");
        out.println("<h1>Number Guessing Game</h1>");
        out.println("<p>Guess a number between 1 and 100:</p>");
        out.println("<form method=\"post\" action=\"" + request.getContextPath() + "/GuessNumber\">");
        out.println("   <input type=\"text\" name=\"guess\">");
        out.println("   <input type=\"submit\" value=\"Guess\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer randomNumber = (Integer) session.getAttribute("randomNumber");
        Integer attempts = (Integer) session.getAttribute("attempts");
        Integer roundsWon = (Integer) session.getAttribute("roundsWon");
        
        int guess = Integer.parseInt(request.getParameter("guess"));
        attempts++;
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        
        if (guess == randomNumber) {
        	out.println("<html><head><title>Number Guessing Game</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"NewFile.css\">");
            out.println("</head><body>");
            out.println("<h2>Congratulations! You guessed the correct number!</h2>");
            out.println("<p>Number of attempts: " + attempts + "</p>");
            if (roundsWon == null) roundsWon = 0;
            roundsWon++;
            session.setAttribute("roundsWon", roundsWon);
            session.removeAttribute("randomNumber");
            session.removeAttribute("attempts");
        } else if (attempts >= MAX_ATTEMPTS) {
        	out.println("<html><head><title>Number Guessing Game</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"NewFile.css\">");
            out.println("</head><body>");
            out.println("<h2>Sorry! You have exhausted all attempts.</h2>");
            out.println("<p>The correct number was: " + randomNumber + "</p>");
            session.removeAttribute("randomNumber");
            session.removeAttribute("attempts");
        } else if (guess < randomNumber) {
        	out.println("<html><head><title>Number Guessing Game</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"NewFile.css\">");
            out.println("</head><body>");
            out.println("<p>Your guess is too low. Try again!</p>");
        } else {
        	out.println("<html><head><title>Number Guessing Game</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"NewFile.css\">");
            out.println("</head><body>");
            out.println("<p>Your guess is too high. Try again!</p>");
        }
        
        session.setAttribute("attempts", attempts);
    }
}