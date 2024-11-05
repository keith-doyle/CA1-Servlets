import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        // Check for password match
        if (password == null || !password.equals(confirmPassword)) {
            out.println("<html><body><h2>Passwords do not match. Please try again.</h2>");
            out.println("<button onclick=\"window.location.href='register.html'\">Back to Register</button></body></html>");
            return;
        }

        // Establish database connection
        try (Connection conn = DBConnection.getConnection()) {
            // Prepare SQL statement
            String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password); // Password should be hashed in production

                // Execute update and check if user was added
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    out.println("<html><body><h2>Registration successful! You have 100 loyalty points.</h2>");
                    out.println("<button onclick=\"window.location.href='login.html'\">Login</button></body></html>");
                } else {
                    out.println("<html><body><h2>Registration failed. Please try again.</h2>");
                    out.println("<button onclick=\"window.location.href='register.html'\">Back to Register</button></body></html>");
                }
            }
        } catch (SQLException e) {
            // Log detailed error information
            out.println("<html><body><h2>Error occurred during registration: " + e.getMessage() + "</h2>");
            out.println("<p>SQL State: " + e.getSQLState() + "</p>");
            out.println("<p>Error Code: " + e.getErrorCode() + "</p>");
            e.printStackTrace(); // Print the stack trace for debugging
            out.println("<button onclick=\"window.location.href='register.html'\">Back to Register</button></body></html>");
        }
    }
}