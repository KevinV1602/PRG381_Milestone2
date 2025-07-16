/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg381_milestone2.Controller;

import prg381_milestone2.Model.Appointment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
/**
 *
 * @author Client
 */
public class AppointmentController {
    private static final String JDBC_URL = "jdbc:derby:wellnessDB;create=true";

    public AppointmentController() {
        // Ensure the JDBC driver is loaded.
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Derby JDBC Driver not found. Make sure derby.jar is in your classpath.");
            e.printStackTrace();
            throw new RuntimeException("Failed to load Derby JDBC driver", e);
        }
        createNewTable(); // Attempt to create the table when the controller is instantiated
    }
     private Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }
     
     private void createNewTable() {
        String sql = "CREATE TABLE appointments (\n"
                   + "    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
                   + "    student_id VARCHAR(50) NOT NULL,\n"
                   + "    student_name VARCHAR(100),\n"
                   + "    counselor_name VARCHAR(100) NOT NULL,\n"
                   + "    appointment_date DATE NOT NULL,\n"
                   + "    appointment_time VARCHAR(50) NOT NULL,\n"
                   + "    status VARCHAR(50) NOT NULL,\n"
                   + "    PRIMARY KEY (id)\n"
                   + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Appointments table checked/created successfully.");
        } catch (SQLException e) {
            // X0Y32 is Derby's SQLState for "table already exists"
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("Appointments table already exists.");
            } else {
                System.err.println("Error creating appointments table: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
     
      public void bookAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments(student_id, student_name, counselor_name, appointment_date, appointment_time, status) VALUES(?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, appointment.getStudentId());
            pstmt.setString(2, appointment.getStudentName());
            pstmt.setString(3, appointment.getCounselorName());
            pstmt.setDate(4, appointment.getAppointmentDate());
            pstmt.setString(5, appointment.getAppointmentTime());
            pstmt.setString(6, appointment.getStatus());
            pstmt.executeUpdate();
        }
    }
     
      public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT id, student_id, student_name, counselor_name, appointment_date, appointment_time, status FROM appointments";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("student_name"),
                    rs.getString("counselor_name"),
                    rs.getDate("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("status")
                ));
            }
        }
        return appointments;
    }
      public List<Appointment> searchAppointmentsByStudentId(String studentId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT id, student_id, student_name, counselor_name, appointment_date, appointment_time, status FROM appointments WHERE LOWER(student_id) LIKE LOWER(?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + studentId + "%"); // Wildcards for LIKE search
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("student_name"),
                    rs.getString("counselor_name"),
                    rs.getDate("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("status")
                ));
            }
        }
        return appointments;
    }
      public void updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET student_id = ?, student_name = ?, counselor_name = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, appointment.getStudentId());
            pstmt.setString(2, appointment.getStudentName());
            pstmt.setString(3, appointment.getCounselorName());
            pstmt.setDate(4, appointment.getAppointmentDate());
            pstmt.setString(5, appointment.getAppointmentTime());
            pstmt.setString(6, appointment.getStatus());
            pstmt.setInt(7, appointment.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating appointment failed, no rows affected for ID: " + appointment.getId());
            }
        }
    }
    public void cancelAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Cancelling appointment failed, no rows affected for ID: " + appointmentId);
            }
        }
    }
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            // XJ015 is the SQLState for a successful shutdown in Derby
            if (se.getSQLState().equals("XJ015")) {
                System.out.println("Derby database shut down normally.");
            } else if (se.getSQLState().equals("08006")) { // Database not found / already shut down
                System.out.println("Derby database already shut down or not found.");
            } else {
                System.err.println("Error shutting down Derby database: " + se.getMessage());
                se.printStackTrace();
            }
        }
    }
       
     
}
