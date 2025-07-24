
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import src.HospitalManagementSystem.Doctor;
import src.HospitalManagementSystem.Patient;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String username = "root";
    private static final String Password = "Patil@92";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, Password);
            Patient patient = new Patient(connection, sc);
            Doctor doctor = new Doctor(connection);
            while (true) {
                System.out.println("Hospital Management System");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patient");
                System.out.println("3.View Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter Your Choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPaatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, sc);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter the valid choice");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner sc) {
        System.out.print("Enter Patient id");
        int pid = sc.nextInt();
        System.out.print("Enter the Doctor Id: ");
        int did = sc.nextInt();
        System.out.print("Enter appointment date(YYYY-MM-DD): ");
        String appointmentDate = sc.next();
        if (patient.geyPatientById(pid) && doctor.geyDocotrById(did)) {
            if (checkDoctorAvailable(did, appointmentDate, connection)) {
                String appointQuery = "Insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointQuery);
                    preparedStatement.setInt(1, pid);
                    preparedStatement.setInt(2, did);
                    preparedStatement.setString(3, appointmentDate);
                    int rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Appointment Book");
                    } else {
                        System.out.println("Failed to book Appointment");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor Not Avilable on this Date");
            }
        } else {
            System.out.println("Either doctor or patient dosent exists");
        }
    }

    public static boolean checkDoctorAvailable(int did, String apointmentDate, Connection connection) {
        String query = "select count(*) from appointments where doctor_id=? and appointment_date=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, did);
            preparedStatement.setString(2, apointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
