package moviecollection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/movie_collection";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "YOUR_MYSQL_PASSWORD"; // Change if your root user has a password

    public static void main(String[] args) {

        try (Connection con =
                     DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            System.out.println("Database Connected Successfully!");

        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
    }
}