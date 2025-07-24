package src.HospitalManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddPatientUI extends JFrame {
    public AddPatientUI(Connection connection) {
        setTitle("Add Patient");
        setSize(300, 300);
        setLayout(new GridLayout(5, 2, 5, 5));
        setLocationRelativeTo(null);

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();
        JButton submitBtn = new JButton("Add Patient");
        JLabel result = new JLabel();

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Gender:"));
        add(genderField);
        add(submitBtn);
        add(result);

        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String gender = genderField.getText().trim();

            String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setInt(2, Integer.parseInt(age));
                stmt.setString(3, gender);
                int rows = stmt.executeUpdate();
                result.setText(rows > 0 ? "✅ Patient added!" : "❌ Failed");
            } catch (SQLException ex) {
                ex.printStackTrace();
                result.setText("❌ Error");
            }
        });

        setVisible(true);
    }
}
