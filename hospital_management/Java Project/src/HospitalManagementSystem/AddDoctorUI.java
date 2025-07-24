package src.HospitalManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddDoctorUI extends JFrame {
    private JTextField nameField;
    private JTextField specializationField;
    private JButton addButton;
    private JLabel resultLabel;
    private Connection connection;

    public AddDoctorUI(Connection connection) {
        this.connection = connection;
        setTitle("Add Doctor");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        nameField = new JTextField();
        specializationField = new JTextField();
        addButton = new JButton("Add Doctor");
        resultLabel = new JLabel("");

        add(new JLabel("Doctor Name:"));
        add(nameField);
        add(new JLabel("Specialization:"));
        add(specializationField);
        add(new JLabel(""));
        add(addButton);
        add(new JLabel(""));
        add(resultLabel);

        addButton.addActionListener(e -> addDoctor());

        setVisible(true);
    }

    private void addDoctor() {
        String name = nameField.getText().trim();
        String specialization = specializationField.getText().trim();

        if (name.isEmpty() || specialization.isEmpty()) {
            resultLabel.setText("❌ All fields are required.");
            return;
        }

        String query = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, specialization);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                resultLabel.setText("✅ Doctor added successfully!");
                nameField.setText("");
                specializationField.setText("");
            } else {
                resultLabel.setText("❌ Failed to add doctor.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultLabel.setText("❌ Error in database.");
        }
    }
}
