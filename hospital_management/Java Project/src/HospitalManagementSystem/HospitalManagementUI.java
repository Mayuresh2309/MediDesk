package src.HospitalManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalManagementUI extends JFrame {
    private Connection connection;

    public HospitalManagementUI() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital_db", "root", "2309");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed");
            System.exit(1);
        }

        setTitle("Hospital Management System");
        setSize(400, 400);
        setLayout(new GridLayout(7, 1, 100, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton addPatientBtn = new JButton("1. Add Patient");
        JButton addDoctorButton = new JButton("2. Add Doctor");
        JButton viewPatientsBtn = new JButton("3. View Patients");
        JButton viewDoctorsBtn = new JButton("4. View Doctors");
        JButton bookAppointmentBtn = new JButton("5. Book Appointment");
        JButton viewAppointmentsButton = new JButton("6. View Appointments");
        JButton exitBtn = new JButton("7. Exit");

        add(addPatientBtn);
        add(addDoctorButton);
        add(viewPatientsBtn);
        add(viewDoctorsBtn);
        add(bookAppointmentBtn);
        add(viewAppointmentsButton);
        add(exitBtn);

        // Button actions
        addPatientBtn.addActionListener(e -> new AddPatientUI(connection));
        addDoctorButton.addActionListener(e -> new AddDoctorUI(connection));
        viewPatientsBtn.addActionListener(e -> new ViewPatientsUI(connection));
        viewDoctorsBtn.addActionListener(e -> new ViewDoctorsUI(connection));
        bookAppointmentBtn.addActionListener(e -> new AppointmentUI(connection));
        viewAppointmentsButton.addActionListener(e -> new ViewAppointmentsUI(connection));
        exitBtn.addActionListener(e -> System.exit(0));
        setVisible(true);
    }

    public static void main(String[] args) {
        new HospitalManagementUI();
    }
}
