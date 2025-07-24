package src.HospitalManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAppointmentsUI extends JFrame {
    private JTable table;
    private Connection connection;

    public ViewAppointmentsUI(Connection connection) {
        this.connection = connection;
        setTitle("View Appointments");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = { "Appointment ID", "Patient ID", "Doctor ID", "Date" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        try {
            String query = "SELECT * FROM appointments";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int aid = rs.getInt("id");
                int pid = rs.getInt("patient_id");
                int did = rs.getInt("doctor_id");
                String date = rs.getString("appointment_date");

                model.addRow(new Object[] { aid, pid, did, date });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving appointments.");
            e.printStackTrace();
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
        setVisible(true);
    }
}
