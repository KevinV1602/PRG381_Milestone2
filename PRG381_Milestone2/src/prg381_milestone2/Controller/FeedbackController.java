/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg381_milestone2.Controller;

/**
 *
 * @author Admin
 */
import prg381_milestone2.Model.Feedback;
import java.sql.*;
import java.util.*;

public class FeedbackController {
    private static final String JDBC_URL = "jdbc:derby:wellnessDB;create=true";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private Connection conn;

    public FeedbackController() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(JDBC_URL);
            createFeedbackTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database or load driver", e);
        }
    }

    private void createFeedbackTable() {
        String sql = "CREATE TABLE FEEDBACK (" +
                     "ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                     "STUDENT_ID VARCHAR(100), " +
                     "COUNSELOR VARCHAR(100), " +
                     "RATING INT, " +
                     "COMMENTS VARCHAR(255))";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // Suppress "table already exists" error
            if (!e.getSQLState().equals("X0Y32")) {
                e.printStackTrace();
            }
        }
    }
    public List<Feedback> searchFeedbackByStudentId(String studentId) throws SQLException {
    List<Feedback> feedbackList = new ArrayList<>();
    String sql = "SELECT * FROM FEEDBACK WHERE STUDENT_ID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, studentId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setId(rs.getInt("ID"));
                fb.setStudentId(rs.getString("STUDENT_ID"));
                fb.setCounselor(rs.getString("COUNSELOR"));
                fb.setRating(rs.getInt("RATING"));
                fb.setComments(rs.getString("COMMENTS"));
                feedbackList.add(fb);
            }
        }
    }
    return feedbackList;
}
    public void updateFeedback(Feedback fb) throws SQLException {
    String sql = "UPDATE FEEDBACK SET STUDENT_ID=?, COUNSELOR=?, RATING=?, COMMENTS=? WHERE ID=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, fb.getStudentId());
        ps.setString(2, fb.getCounselor());
        ps.setInt(3, fb.getRating());
        ps.setString(4, fb.getComments());
        ps.setInt(5, fb.getId());
        ps.executeUpdate();
    }
}

    public void submitFeedback(Feedback fb) throws SQLException {
        String sql = "INSERT INTO FEEDBACK (STUDENT_ID, COUNSELOR, RATING, COMMENTS) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fb.getStudentId());
            ps.setString(2, fb.getCounselor());
            ps.setInt(3, fb.getRating());
            ps.setString(4, fb.getComments());
            ps.executeUpdate();
        }
    }
    public void deleteFeedback(int id) throws SQLException {
    String sql = "DELETE FROM FEEDBACK WHERE ID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}

    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM FEEDBACK";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setId(rs.getInt("ID"));
                fb.setStudentId(rs.getString("STUDENT_ID"));
                fb.setCounselor(rs.getString("COUNSELOR"));
                fb.setRating(rs.getInt("RATING"));
                fb.setComments(rs.getString("COMMENTS"));
                feedbackList.add(fb);
            }
        }
        return feedbackList;
    }
}
