import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard") // URL mapping for this servlet
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the current session, return null if it doesn't exist
        HttpSession session = request.getSession(false);
        // Check if the user is not logged in, redirect to login page
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // Retrieve user information from session
        String username = (String) session.getAttribute("username");
        int points = (Integer) session.getAttribute("points");

        // Create the HTML response
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><title>Dashboard</title></head><body>");
        out.println("<h1>Welcome, " + username + "!</h1>");
        out.println("<p>Your current loyalty points: " + points + "</p>");

        // Form to add points
        out.println("<form action='addPoints' method='post'>");
        out.println("<label for='addAmount'>Add Points:</label>");
        out.println("<input type='number' name='addAmount' min='1' required>");
        out.println("<input type='submit' value='Add Points'>");
        out.println("</form>");

        // Form to spend points
        out.println("<form action='spendPoints' method='post'>");
        out.println("<label for='spendAmount'>Spend Points:</label>");
        out.println("<input type='number' name='spendAmount' min='1' required>");
        out.println("<input type='submit' value='Spend Points'>");
        out.println("</form>");
        
        // Logout button redirects to index.html
        out.println("<button onclick=\"window.location.href='index.html'\">Logout</button>");
        out.println("</body></html>");
    }
}