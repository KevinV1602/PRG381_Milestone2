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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String username = request.getParameter("username").trim();
    String password = request.getParameter("password").trim();

    System.out.println("Login attempt for: " + username);

    try {
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5353/PRG381_wellness", 
                "postgres", 
                "Ven06246"
        );
        System.out.println("DB connected: " + conn.isValid(2));

        // Hash generation debug
        String hashedPassword = hashPassword(password);
        System.out.println("Generated hash: " + hashedPassword);
        System.out.println("Test hash for '12345678': " + hashPassword("12345678"));

        // Query debug
        PreparedStatement ps = conn.prepareStatement(
                "SELECT username, password_hash, first_name FROM students WHERE username = ? AND password_hash = ?"
        );
        ps.setString(1, username);
        ps.setString(2, hashedPassword);
        
        System.out.println("Executing query: " + ps.toString());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("LOGIN SUCCESS - User found:");
            System.out.println("Name: " + rs.getString("username"));
            System.out.println("DB Hash: " + rs.getString("password_hash"));
            
            HttpSession session = request.getSession();
            session.setAttribute("studentName", rs.getString("first_name"));
            response.sendRedirect("dashboard.jsp");
        } else {
            System.out.println("LOGIN FAILED - No matching record");
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        conn.close();
    } catch (Exception e) {
        System.out.println("EXCEPTION: " + e.getMessage());
        e.printStackTrace();
        request.setAttribute("error", "System error: " + e.getMessage());
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
