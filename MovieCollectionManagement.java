package moviecollection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.ResultSet;

public class MovieCollectionManagement {

    static String url = "jdbc:mysql://localhost:3306/movie_collection";
    static String username = "root";
    static String password = "YOUR_MYSQL_PASSWORD";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, username, password);

            System.out.println("Database Connected Successfully!");

            int choice;

            do {
                System.out.println("\n====================================");
                System.out.println("MOVIE COLLECTION MANAGEMENT SYSTEM");
                System.out.println("====================================");

                System.out.println("1. Add Movie");
                System.out.println("2. View Movies");
                System.out.println("3. Edit Movie");
                System.out.println("4. Update Movie");
                System.out.println("5. Delete Movie");
                System.out.println("6. Exit");

                System.out.print("\nEnter your choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        addMovie(con,sc);
                        break;

                    case 2:
                        viewMovies(con);
                        break;

                    case 3:
                        editMovie(con,sc);
                        break;

                    case 4:
                        updateMovie(con,sc);
                        break;

                    case 5:
                        deleteMovie(con,sc);
                        break;

                    case 6:
                        System.out.println("Exiting program...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 6);

            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        sc.close();
    }
    
   
    static void addMovie(Connection con, Scanner sc) {

        try {
            sc.nextLine(); // Clear the leftover newline

            System.out.print("Enter movie title: ");
            String title = sc.nextLine();

            System.out.print("Enter movie genre: ");
            String genre = sc.nextLine();

            System.out.print("Enter release year: ");
            int releaseYear = sc.nextInt();

            System.out.print("Enter movie rating: ");
            double rating = sc.nextDouble();

            String sql = "INSERT INTO movies "
                       + "(title, genre, release_year, rating) "
                       + "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, title);
                ps.setString(2, genre);
                ps.setInt(3, releaseYear);
                ps.setDouble(4, rating);

                ps.executeUpdate();

                System.out.println("Movie added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding movie!");
            e.printStackTrace();
        }
    }
    
    static void viewMovies(Connection con) {

        String sql = "SELECT movie_id, title, genre, release_year, rating "
                   + "FROM movies";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            System.out.println("\n========== MOVIE LIST ==========");

            while (rs.next()) {
                found = true;

                System.out.println("Movie ID: " + rs.getInt("movie_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Genre: " + rs.getString("genre"));
                System.out.println("Release Year: " + rs.getInt("release_year"));
                System.out.println("Rating: " + rs.getDouble("rating"));
                System.out.println("--------------------------------");
            }

            if (!found) {
                System.out.println("No movies found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing movies!");
            e.printStackTrace();
        }
    }
    
    static void editMovie(Connection con, Scanner sc) {

        try {
            System.out.print("Enter the movie ID to edit: ");
            int movieId = sc.nextInt();
            sc.nextLine(); // Clear the leftover newline

            System.out.print("Enter new movie title: ");
            String title = sc.nextLine();

            System.out.print("Enter new movie genre: ");
            String genre = sc.nextLine();

            System.out.print("Enter new release year: ");
            int releaseYear = sc.nextInt();

            System.out.print("Enter new movie rating: ");
            double rating = sc.nextDouble();

            String sql = "UPDATE movies "
                       + "SET title = ?, genre = ?, release_year = ?, rating = ? "
                       + "WHERE movie_id = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, title);
                ps.setString(2, genre);
                ps.setInt(3, releaseYear);
                ps.setDouble(4, rating);
                ps.setInt(5, movieId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Movie updated successfully!");
                } else {
                    System.out.println("Movie ID not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error editing movie!");
            e.printStackTrace();
        }
    }
    
    static void updateMovie(Connection con, Scanner sc) {

        try {
            System.out.print("Enter the movie ID to update: ");
            int movieId = sc.nextInt();
            sc.nextLine(); // Clear the leftover newline

            System.out.print("Enter updated movie title: ");
            String title = sc.nextLine();

            System.out.print("Enter updated movie genre: ");
            String genre = sc.nextLine();

            System.out.print("Enter updated release year: ");
            int releaseYear = sc.nextInt();

            System.out.print("Enter updated movie rating: ");
            double rating = sc.nextDouble();

            String sql = "UPDATE movies "
                       + "SET title = ?, genre = ?, release_year = ?, rating = ? "
                       + "WHERE movie_id = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, title);
                ps.setString(2, genre);
                ps.setInt(3, releaseYear);
                ps.setDouble(4, rating);
                ps.setInt(5, movieId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Movie updated successfully!");
                } else {
                    System.out.println("Movie ID not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error updating movie!");
            e.printStackTrace();
        }
    }
    
    static void deleteMovie(Connection con, Scanner sc) {

        try {
            System.out.print("Enter the movie ID to delete: ");
            int movieId = sc.nextInt();

            String sql = "DELETE FROM movies WHERE movie_id = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, movieId);

                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Movie deleted successfully!");
                } else {
                    System.out.println("Movie ID not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error deleting movie!");
            e.printStackTrace();
        }
    }
}