package src.prg381_milestone2.Controller;

import src.prg381_milestone2.Model.Counselor;
import java.sql.*;
import java.util.*;

public class CounselorController {

    private static final String DB_URL = "jdbc:derby:wellnessDB;create=true";
    private Connection conn;


    public CounselorController() throws SQLException {
        this.conn = DriverManager.getConnection(DB_URL);
    }

    public void addCounselor(String name, String specialization, String availability) throws Exception {
        if (name.isEmpty() || specialization.isEmpty() || availability.isEmpty()) {
            throw new Exception("All fields are required.");
        }

        String sql = "INSERT INTO Counselors (name, specialization, availability) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, specialization);
            stmt.setString(3, availability);
            stmt.executeUpdate();
        }
    }

    public List<Counselor> getAllCounselors() throws Exception {
        List<Counselor> list = new ArrayList<>();
        String sql = "SELECT * FROM Counselors";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Counselor(
                        rs.getInt("counselor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("availability")
                ));
            }
        }

        return list;
    }

    public void updateCounselor(int id, String name, String specialization, String availability) throws Exception {
        if (name.isEmpty() || specialization.isEmpty() || availability.isEmpty()) {
            throw new Exception("All fields are required.");
        }

        String sql = "UPDATE Counselors SET name=?, specialization=?, availability=? WHERE counselor_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, specialization);
            stmt.setString(3, availability);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void deleteCounselor(int id) throws Exception {
        String sql = "DELETE FROM Counselors WHERE counselor_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

