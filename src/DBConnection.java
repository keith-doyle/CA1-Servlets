import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database URL, including the name of the database and the server timezone.
    private static final String URL = "jdbc:mysql://localhost:3306/loyaltyapp?serverTimezone=UTC"; 
    // Database username for authentication
    private static final String USER = "root"; 
    // Database password for authentication
    private static final String PASSWORD = "root"; 

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD); // Returns a connection object
    }
}