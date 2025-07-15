package com.group_P1.wellness.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password_hash");

        // Validate input
        if (!isValidEmail(email) || !isValidPhone(phone) || !isStrongPassword(password)) {
            request.setAttribute("error", "Invalid input. Please check your details.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            // Connect to PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Check for duplicates
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5353/PRG381_wellness", "postgres", "Ven06246")) {
                // Check for duplicates
                PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM students WHERE email = ?");
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next()) {
                    request.setAttribute("error", "Email or Student Number already registered.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else {String confirmPassword = request.getParameter("confirm_password");
                if (!password.equals(confirmPassword)){
                    request.setAttribute("error", "Passwords do not match.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;}
                }
                
                // Hash password
                { String hashedPassword = hashPassword(password);

                // Save user
                PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO students (first_name, last_name, username, email, phone, password_hash) VALUES (?, ?, ?, ?, ?, ?)");
                insertStmt.setString(1, firstName);
                insertStmt.setString(2, lastName);
                insertStmt.setString(3, username);
                insertStmt.setString(4, email);
                insertStmt.setString(5, phone);
                insertStmt.setString(6, hashedPassword);

                insertStmt.executeUpdate();
                request.setAttribute("message", "Registration successful!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            }

        } catch (ServletException | IOException | ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
            request.setAttribute("error", "An error occurred during registration.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // Validation methods
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    public static boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[a-z].*");
    }

    // Hashing method
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}
