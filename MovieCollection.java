package loginproject;

import javax.swing.*;
import java.awt.*;

public class MovieCollection extends JFrame {

    JButton moviesButton;
    JButton directorsButton;
    JButton genresButton;
    JButton logoutButton;

    public MovieCollection() {

        setTitle("Movie Collection Management");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("MOVIE COLLECTION MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(50, 40, 500, 40);

        JLabel welcomeLabel = new JLabel("Select an option");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(50, 90, 500, 30);

        moviesButton = new JButton("Movies");
        moviesButton.setBounds(100, 160, 180, 50);

        directorsButton = new JButton("Directors");
        directorsButton.setBounds(320, 160, 180, 50);

        genresButton = new JButton("Genres");
        genresButton.setBounds(100, 240, 180, 50);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(320, 240, 180, 50);

        mainPanel.add(titleLabel);
        mainPanel.add(welcomeLabel);
        mainPanel.add(moviesButton);
        mainPanel.add(directorsButton);
        mainPanel.add(genresButton);
        mainPanel.add(logoutButton);

        add(mainPanel);
        
        moviesButton.addActionListener(e -> {
            new Movies();
        });
        
        directorsButton.addActionListener(e -> {
            new Directors();
        });
        
        genresButton.addActionListener(e -> {
            new Genres();
        });
        
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });

        setVisible(true);
    }

    public static void main(String[] args) {

        new MovieCollection();
    }
}
