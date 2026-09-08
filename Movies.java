package loginproject;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class Movies extends JFrame {

    JButton addButton;
    JButton viewButton;
    JButton updateButton;
    JButton deleteButton;
    JButton backButton;

    public Movies() {

        setTitle("Movies Management");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("MOVIES MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(100, 40, 400, 40);

        addButton = new JButton("Add Movie");
        addButton.setBounds(100, 130, 180, 50);

        viewButton = new JButton("View Movies");
        viewButton.setBounds(320, 130, 180, 50);

        updateButton = new JButton("Update Movie");
        updateButton.setBounds(100, 210, 180, 50);

        deleteButton = new JButton("Delete Movie");
        deleteButton.setBounds(320, 210, 180, 50);

        backButton = new JButton("Back");
        backButton.setBounds(220, 300, 160, 40);

        mainPanel.add(titleLabel);
        mainPanel.add(addButton);
        mainPanel.add(viewButton);
        mainPanel.add(updateButton);
        mainPanel.add(deleteButton);
        mainPanel.add(backButton);

        add(mainPanel);

        addButton.addActionListener(e -> addMovie());
        viewButton.addActionListener(e -> viewMovies());
        updateButton.addActionListener(e -> updateMovie());
        deleteButton.addActionListener(e -> deleteMovie());
        
        backButton.addActionListener(e -> {
            dispose();
            new MovieCollection();
        });

        setVisible(true);
    }

    private void addMovie() {

        JTextField titleField = new JTextField();
        JTextField releaseDateField = new JTextField();
        JTextField languageField = new JTextField();
        JTextField ratingField = new JTextField();
        JTextField directorField = new JTextField();
        JTextField genreField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Movie Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Release Date (YYYY-MM-DD):"));
        panel.add(releaseDateField);

        panel.add(new JLabel("Language:"));
        panel.add(languageField);

        panel.add(new JLabel("Rating:"));
        panel.add(ratingField);

        panel.add(new JLabel("Director ID:"));
        panel.add(directorField);

        panel.add(new JLabel("Genre ID:"));
        panel.add(genreField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Movie",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            String title = titleField.getText();
            String releaseDate = releaseDateField.getText();
            String language = languageField.getText();
            String rating = ratingField.getText();
            String directorId = directorField.getText();
            String genreId = genreField.getText();

            

            try {
            	Connection con = DatabaseConnection.getConnection();

                String sql = "INSERT INTO movies " +
                        "(title, release_date, language, rating, director_id, genre_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, title);
                ps.setString(2, releaseDate);
                ps.setString(3, language);
                ps.setDouble(4, Double.parseDouble(rating));
                ps.setInt(5, Integer.parseInt(directorId));
                ps.setInt(6, Integer.parseInt(genreId));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Movie Added Successfully!"
                );

                ps.close();
                con.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage()
                );
            }
        }
    }
    
    public void viewMovies() {

        

        try {

        	Connection con = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM movies";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            String[] columns = {
                    "Movie ID",
                    "Title",
                    "Release Date",
                    "Language",
                    "Rating",
                    "Director ID",
                    "Genre ID"
            };

            DefaultTableModel model = new DefaultTableModel(columns, 0);

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getDate("release_date"),
                        rs.getString("language"),
                        rs.getDouble("rating"),
                        rs.getInt("director_id"),
                        rs.getInt("genre_id")
                };

                model.addRow(row);
            }

            JTable table = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(table);

            JFrame viewFrame = new JFrame("Movie Collection");
            viewFrame.setSize(800, 400);
            viewFrame.setLocationRelativeTo(this);

            viewFrame.add(scrollPane);

            viewFrame.setVisible(true);

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex);
        }
    }
    
    public void updateMovie() {

        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField releaseDateField = new JTextField();
        JTextField languageField = new JTextField();
        JTextField ratingField = new JTextField();
        JTextField directorField = new JTextField();
        JTextField genreField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        panel.add(new JLabel("Movie ID:"));
        panel.add(idField);

        panel.add(new JLabel("Movie Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Release Date (YYYY-MM-DD):"));
        panel.add(releaseDateField);

        panel.add(new JLabel("Language:"));
        panel.add(languageField);

        panel.add(new JLabel("Rating:"));
        panel.add(ratingField);

        panel.add(new JLabel("Director ID:"));
        panel.add(directorField);

        panel.add(new JLabel("Genre ID:"));
        panel.add(genreField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Update Movie",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            String id = idField.getText();
            String title = titleField.getText();
            String releaseDate = releaseDateField.getText();
            String language = languageField.getText();
            String rating = ratingField.getText();
            String directorId = directorField.getText();
            String genreId = genreField.getText();

            

            try {

            	Connection con = DatabaseConnection.getConnection();

                String sql = "UPDATE movies SET " +
                        "title = ?, " +
                        "release_date = ?, " +
                        "language = ?, " +
                        "rating = ?, " +
                        "director_id = ?, " +
                        "genre_id = ? " +
                        "WHERE movie_id = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, title);
                ps.setString(2, releaseDate);
                ps.setString(3, language);
                ps.setDouble(4, Double.parseDouble(rating));
                ps.setInt(5, Integer.parseInt(directorId));
                ps.setInt(6, Integer.parseInt(genreId));
                ps.setInt(7, Integer.parseInt(id));

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Movie Updated Successfully!");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Movie ID not found!");
                }

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex);
            }
        }
    }
    
    public void deleteMovie() {

        String movieId = JOptionPane.showInputDialog(
                this,
                "Enter Movie ID to delete:"
        );

        if (movieId == null || movieId.trim().isEmpty()) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete Movie ID " + movieId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        

        try {

        	Connection con = DatabaseConnection.getConnection();

            String sql = "DELETE FROM movies WHERE movie_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(movieId));

            int rows = ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Movie Deleted Successfully!");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Movie ID not found!");
            }

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex);
        }
    }

    public static void main(String[] args) {
        new Movies();
    }
}
