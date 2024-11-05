import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login") // URL mapping for this servlet
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the username and password from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Establish a database connection
        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to check for valid username and password
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username); // Set the first parameter (username)
            stmt.setString(2, password); // Set the second parameter (password)
            ResultSet rs = stmt.executeQuery(); // Execute the query

            // If a matching user is found
            if (rs.next()) {
                // Create a new session and store user information
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("points", rs.getInt("points")); // Store the user's points
                // Redirect to the dashboard
                response.sendRedirect("dashboard");
            } else {
                // If no match is found, display an error message
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<html><body><h2>Invalid credentials. Please try again.</h2>");
                out.println("<button onclick=\"window.location.href='login.html'\">Back to Login</button></body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging if an SQL error occurs
        }
    }
}
