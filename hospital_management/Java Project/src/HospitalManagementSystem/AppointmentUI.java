package src.HospitalManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AppointmentUI extends JFrame {
    private JTextField patientIdField;
    private JTextField doctorIdField;
    private JTextField dateField;
    private JButton bookButton;
    private JLabel resultLabel;

    private Connection connection;

    public AppointmentUI(Connection connection) {
        this.connection = connection;
        setTitle("Book Appointment");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        patientIdField = new JTextField();
        doctorIdField = new JTextField();
        dateField = new JTextField();
        bookButton = new JButton("Book Appointment");
        resultLabel = new JLabel("");

        add(new JLabel("Patient ID:"));
        add(patientIdField);
        add(new JLabel("Doctor ID:"));
        add(doctorIdField);
        add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        add(dateField);
        add(new JLabel(""));
        add(bookButton);
        add(new JLabel(""));
        add(resultLabel);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookAppointment();
            }
        });

        setVisible(true);
    }

    private void bookAppointment() {
        try {
            int pid = Integer.parseInt(patientIdField.getText().trim());
            int did = Integer.parseInt(doctorIdField.getText().trim());
            String date = dateField.getText().trim();

            if (!recordExists("SELECT * FROM patients WHERE id = ?", pid)) {
                resultLabel.setText("❌ Patient not found");
                return;
            }

            if (!recordExists("SELECT * FROM doctors WHERE id = ?", did)) {
                resultLabel.setText("❌ Doctor not found");
                return;
            }

            if (!isDoctorAvailable(did, date)) {
                resultLabel.setText("❌ Doctor not available on " + date);
                return;
            }

            String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, pid);
            stmt.setInt(2, did);
            stmt.setString(3, date);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                resultLabel.setText("✅ Appointment booked!");
            } else {
                resultLabel.setText("❌ Failed to book appointment");
            }

        } catch (NumberFormatException ex) {
            resultLabel.setText("❌ Enter valid numbers");
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultLabel.setText("❌ Database error");
        }
    }

    private boolean recordExists(String query, int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    private boolean isDoctorAvailable(int did, String date) throws SQLException {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, did);
        stmt.setString(2, date);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) == 0;
        }
        return false;
    }

    // Entry point to test the UI separately
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital_db", "root", "Patil@92");
            new AppointmentUI(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
