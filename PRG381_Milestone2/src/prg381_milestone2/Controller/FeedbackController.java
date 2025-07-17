/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg381_milestone2.Controller;

/**
 *
 * @author Admin
 */

import java.sql.*;
import java.util.*;
import prg381_milestone2.Model.Feedback;

public class FeedbackController {
    private final Connection conn;

    public FeedbackController(Connection conn) {
        this.conn = conn;
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

    public List<Feedback> getFeedbackByStudentId(String studentId) throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM FEEDBACK WHERE STUDENT_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setId(rs.getInt("ID"));
                fb.setStudentId(rs.getString("STUDENT_ID"));
                fb.setCounselor(rs.getString("COUNSELOR"));
                fb.setRating(rs.getInt("RATING"));
                fb.setComments(rs.getString("COMMENTS"));
                list.add(fb);
            }
        }
        return list;
    }

    public void updateFeedback(Feedback fb) throws SQLException {
        String sql = "UPDATE FEEDBACK SET COUNSELOR = ?, RATING = ?, COMMENTS = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fb.getCounselor());
            ps.setInt(2, fb.getRating());
            ps.setString(3, fb.getComments());
            ps.setInt(4, fb.getId());
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
}

