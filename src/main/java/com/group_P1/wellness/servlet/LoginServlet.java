package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Example DB setup — adjust credentials and URL
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5353/PRG381_wellness", "postgres", "Ven06246"
            );

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM students WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Success: store name in session and redirect
                HttpSession session = request.getSession();
                session.setAttribute("studentName", rs.getString("name"));
                response.sendRedirect("dashboard.jsp");
            } else {
                // Failure: redirect back to login with error
                response.sendRedirect("login.jsp?error=invalid");
            }
            System.out.println("Login query executed. Match found: " + rs.next());

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=exception");
        }
    }
}
