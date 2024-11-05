import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addPoints")
public class AddPointsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int addAmount = Integer.parseInt(request.getParameter("addAmount"));
        String username = (String) session.getAttribute("username");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Users SET points = points + ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, addAmount);
            stmt.setString(2, username);
            stmt.executeUpdate();

            int newPoints = (Integer) session.getAttribute("points") + addAmount;
            session.setAttribute("points", newPoints);

            response.sendRedirect("dashboard");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}