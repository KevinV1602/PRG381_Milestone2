package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Protect access
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentName") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Forward to feedback form
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String type = request.getParameter("type");
        String comments = request.getParameter("comments");
        HttpSession session = request.getSession(false);
        String studentName = (String) session.getAttribute("studentName");

        if (comments == null || comments.trim().isEmpty()) {
            request.setAttribute("error", "Please provide feedback comments.");
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5353/PRG381_wellness", "postgres", "Ven06246"
            );

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO feedback (student_name, feedback_type, comments) VALUES (?, ?, ?)"
            );

            ps.setString(1, studentName);
            ps.setString(2, type);
            ps.setString(3, comments);

            ps.executeUpdate();
            conn.close();

            request.setAttribute("message", "Thank you for your feedback!");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not submit feedback. Please try again.");
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
        }
    }
}
