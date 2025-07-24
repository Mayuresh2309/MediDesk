package src.HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;
    public Patient(Connection connection,Scanner sc){
        this.connection=connection;
        this.sc=sc;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name ");
        String name=sc.next();
        System.out.print("Enter Patient age: ");
        int age=sc.nextInt();
        System.out.println("Enter Patient Gender ");
        String gender=sc.next();

        try {
            String query="Insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3,gender);
            int rows=preparedStatement.executeUpdate();
            if(rows>0){
                System.out.println("Patient added Successfully");
            }else{
                System.out.println("Failed to add Patient");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPaatients(){
        String query="select * from patients";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients ");
            System.out.println("+------------+--------------------------+----------+---------------+");
            System.out.println("| Patient Id |           Name           |  Age     |  Gender       |");
            System.out.println("+------------+--------------------------+----------+---------------+");
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("| %-10s | %-24s | %-8s | %-13s |\n",id,name,age,gender);
                System.out.println("+------------+--------------------------+----------+---------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean geyPatientById(int id){
        String query="Select * from patients where id=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;

            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
