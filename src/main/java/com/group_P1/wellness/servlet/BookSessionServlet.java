package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/BookSessionServlet")
public class BookSessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Protect access — must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentName") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Show booking form
        request.getRequestDispatcher("bookSession.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String type = request.getParameter("type");

        HttpSession session = request.getSession(false);
        String studentName = (String) session.getAttribute("studentName");

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5353/PRG381_wellness", "postgres", "Ven06246"
            );

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO appointments (student_name, session_date, session_time, session_type) VALUES (?, ?, ?, ?)"
            );

            ps.setString(1, studentName);
            ps.setString(2, date);
            ps.setString(3, time);
            ps.setString(4, type);

            ps.executeUpdate();
            conn.close();

            request.setAttribute("message", "Your session has been booked!");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not book session. Try again later.");
            request.getRequestDispatcher("bookSession.jsp").forward(request, response);
        }
    }
}
