package src.HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;
    public Doctor(Connection connection){
        this.connection=connection;
    }

    public void viewDoctors(){
        String query="select * from Doctors";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Doctors ");
            System.out.println("+------------+--------------------------+-----------------------+");
            System.out.println("| Doctor  Id |           Name           |  Specialization       |");
            System.out.println("+------------+--------------------------+-----------------------+");
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String spec=resultSet.getString("specialization");
                System.out.printf("| %-10s | %-24s | %-21s |\n",id,name,spec);
                System.out.println("+------------+--------------------------+-----------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean geyDocotrById(int id){
        String query="Select * from Doctors where id=?";
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
