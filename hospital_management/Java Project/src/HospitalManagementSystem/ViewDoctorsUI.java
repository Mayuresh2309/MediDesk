package src.HospitalManagementSystem;

import javax.swing.*;
import java.sql.*;

public class ViewDoctorsUI extends JFrame {
    public ViewDoctorsUI(Connection connection) {
        setTitle("Doctors List");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(area);
        add(scrollPane);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Name: ").append(rs.getString("name"))
                  .append(", Speciality: ").append(rs.getString("specialization"))
                  .append("\n");
            }
            area.setText(sb.toString());
        } catch (SQLException ex) {
            area.setText("Error loading doctors");
            ex.printStackTrace();
        }

        setVisible(true);
    }
}
