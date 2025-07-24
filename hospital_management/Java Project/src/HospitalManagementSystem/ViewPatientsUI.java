package src.HospitalManagementSystem;

import javax.swing.*;
import java.sql.*;

public class ViewPatientsUI extends JFrame {
    public ViewPatientsUI(Connection connection) {
        setTitle("Patients List");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(area);
        add(scrollPane);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Name: ").append(rs.getString("name"))
                  .append(", Age: ").append(rs.getInt("age"))
                  .append(", Gender: ").append(rs.getString("gender"))
                  .append("\n");
            }
            area.setText(sb.toString());
        } catch (SQLException ex) {
            area.setText("Error loading patients");
            ex.printStackTrace();
        }
        setVisible(true);
    }
}
