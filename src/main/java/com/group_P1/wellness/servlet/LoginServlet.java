package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Base64;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Connect to DB
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/PRG381_wellness", "postgres", "Ven06246"
            );

            // Hash password input
            String hashedPassword = hashPassword(password);

            // Query with hashed password
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM students WHERE username = ? AND password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("studentName", rs.getString("name"));

                // Optional: add other attributes like studentNumber or email
                // session.setAttribute("studentNumber", rs.getString("student_number"));

                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid login credentials.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Password hashing method
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
