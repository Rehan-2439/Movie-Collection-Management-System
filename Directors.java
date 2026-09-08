package loginproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Directors extends JFrame {

    JButton addButton;
    JButton viewButton;
    JButton updateButton;
    JButton deleteButton;
    JButton backButton;

    public Directors() {

        setTitle("Directors Management");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("DIRECTORS MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(100, 40, 400, 40);

        addButton = new JButton("Add Director");
        addButton.setBounds(100, 130, 180, 50);

        viewButton = new JButton("View Directors");
        viewButton.setBounds(320, 130, 180, 50);

        updateButton = new JButton("Update Director");
        updateButton.setBounds(100, 210, 180, 50);

        deleteButton = new JButton("Delete Director");
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

        addButton.addActionListener(e -> addDirector());
        viewButton.addActionListener(e -> viewDirectors());
        updateButton.addActionListener(e -> updateDirector());
        deleteButton.addActionListener(e -> deleteDirector());

        backButton.addActionListener(e -> {
            dispose();
            new MovieCollection();
        });

        setVisible(true);
    }

    public void addDirector() {

        JTextField nameField = new JTextField();
        JTextField nationalityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));

        panel.add(new JLabel("Director Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Nationality:"));
        panel.add(nationalityField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Director",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {


            try {

            	Connection con = DatabaseConnection.getConnection();

                String sql = "INSERT INTO directors " +
                        "(director_name, nationality) VALUES (?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nameField.getText());
                ps.setString(2, nationalityField.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Director Added Successfully!");

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex);
            }
        }
    }

    public void viewDirectors() {


        try {

        	Connection con = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM directors";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String[] columns = {
                    "Director ID",
                    "Director Name",
                    "Nationality"
            };

            DefaultTableModel model = new DefaultTableModel(columns, 0);

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("director_id"),
                        rs.getString("director_name"),
                        rs.getString("nationality")
                };

                model.addRow(row);
            }

            JTable table = new JTable(model);

            JFrame viewFrame = new JFrame("Directors");
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

    public void updateDirector() {

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField nationalityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Director ID:"));
        panel.add(idField);

        panel.add(new JLabel("Director Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Nationality:"));
        panel.add(nationalityField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Update Director",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

            	Connection con = DatabaseConnection.getConnection();

                String sql = "UPDATE directors SET " +
                        "director_name = ?, " +
                        "nationality = ? " +
                        "WHERE director_id = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nameField.getText());
                ps.setString(2, nationalityField.getText());
                ps.setInt(3, Integer.parseInt(idField.getText()));

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Director Updated Successfully!");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Director ID not found!");
                }

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex);
            }
        }
    }

    public void deleteDirector() {

        String id = JOptionPane.showInputDialog(
                this,
                "Enter Director ID to delete:"
        );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete Director ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }


        try {

        	Connection con = DatabaseConnection.getConnection();

            String sql = "DELETE FROM directors WHERE director_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(id));

            int rows = ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Director Deleted Successfully!");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Director ID not found!");
            }

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex);
        }
    }

    public static void main(String[] args) {
        new Directors();
    }
}