import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {
private static Connection connection;
    
    private DatabaseConnectivity() {}

    public static synchronized Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        else {
            try {
                // set the db url, username, and password
                String url = "jdbc:mysql://localhost:3306/customer";
                String username = "root";
                String password = "Aksh2530$";

                // get and return connection
                connection = DriverManager.getConnection(
                        url, username, password);
                return connection;
            } catch (SQLException e) {
                System.out.println(e);
                return null;
            }            
        }
    }
    
    public static synchronized void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                connection = null;                
            }
        }
    }
}
