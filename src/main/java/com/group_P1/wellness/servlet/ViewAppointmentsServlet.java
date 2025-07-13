package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/ViewAppointmentsServlet")
public class ViewAppointmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String studentName = (session != null) ? (String) session.getAttribute("studentName") : null;

        if (studentName == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5353/PRG381_wellness", "postgres", "Ven06246"
            );

            PreparedStatement ps = conn.prepareStatement(
                "SELECT session_date, session_time, session_type FROM appointments WHERE student_name = ?"
            );
            ps.setString(1, studentName);
            ResultSet rs = ps.executeQuery();

            ArrayList<String> appointmentList = new ArrayList<>();
            while (rs.next()) {
                String entry = rs.getString("session_date") + " at " +
                               rs.getString("session_time") + " — " +
                               rs.getString("session_type");
                appointmentList.add(entry);
            }

            request.setAttribute("appointments", appointmentList);
            request.getRequestDispatcher("viewAppointments.jsp").forward(request, response);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve appointments.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
}
