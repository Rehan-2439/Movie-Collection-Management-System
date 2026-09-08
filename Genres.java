package loginproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Genres extends JFrame {

    JButton addButton;
    JButton viewButton;
    JButton updateButton;
    JButton deleteButton;
    JButton backButton;

    public Genres() {

        setTitle("Genres Management");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("GENRES MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(100, 40, 400, 40);

        addButton = new JButton("Add Genre");
        addButton.setBounds(100, 130, 180, 50);

        viewButton = new JButton("View Genres");
        viewButton.setBounds(320, 130, 180, 50);

        updateButton = new JButton("Update Genre");
        updateButton.setBounds(100, 210, 180, 50);

        deleteButton = new JButton("Delete Genre");
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

        addButton.addActionListener(e -> addGenre());
        viewButton.addActionListener(e -> viewGenres());
        updateButton.addActionListener(e -> updateGenre());
        deleteButton.addActionListener(e -> deleteGenre());

        backButton.addActionListener(e -> {
            dispose();
            new MovieCollection();
        });

        setVisible(true);
    }

    public void addGenre() {

        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));

        panel.add(new JLabel("Genre Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Genre",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {


            try {

            	Connection con = DatabaseConnection.getConnection();

                String sql = "INSERT INTO genres " +
                        "(genre_name, description) VALUES (?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nameField.getText());
                ps.setString(2, descriptionField.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Genre Added Successfully!");

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex);
            }
        }
    }

    public void viewGenres() {

        try {

        	Connection con = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM genres";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String[] columns = {
                    "Genre ID",
                    "Genre Name",
                    "Description"
            };

            DefaultTableModel model = new DefaultTableModel(columns, 0);

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("genre_id"),
                        rs.getString("genre_name"),
                        rs.getString("description")
                };

                model.addRow(row);
            }

            JTable table = new JTable(model);

            JFrame viewFrame = new JFrame("Genres");
            viewFrame.setSize(600, 350);
            viewFrame.setLocationRelativeTo(this);

            viewFrame.add(new JScrollPane(table));
            viewFrame.setVisible(true);

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex);
        }
    }

    public void updateGenre() {

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Genre ID:"));
        panel.add(idField);

        panel.add(new JLabel("Genre Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Update Genre",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

         

           try {

            	Connection con = DatabaseConnection.getConnection();

                String sql = "UPDATE genres SET " +
                        "genre_name = ?, " +
                        "description = ? " +
                        "WHERE genre_id = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nameField.getText());
                ps.setString(2, descriptionField.getText());
                ps.setInt(3, Integer.parseInt(idField.getText()));

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Genre Updated Successfully!");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Genre ID not found!");
                }

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex);
            }
        }
    }

    public void deleteGenre() {

        String id = JOptionPane.showInputDialog(
                this,
                "Enter Genre ID to delete:"
        );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete Genre ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        

        try {

        	Connection con = DatabaseConnection.getConnection();

            String sql = "DELETE FROM genres WHERE genre_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(id));

            int rows = ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Genre Deleted Successfully!");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Genre ID not found!");
            }

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex);
        }
    }

    public static void main(String[] args) {
        new Genres();
    }
}
