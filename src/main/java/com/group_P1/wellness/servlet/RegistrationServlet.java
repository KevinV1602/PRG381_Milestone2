package com.group_P1.wellness.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.security.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import com.zaxxer.hikari.*;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    
    private HikariDataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            // 1. Force driver registration
            Class.forName("org.postgresql.Driver");
            
            // 2. Configure connection pool with enhanced settings
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5353/PRG381_wellness");
            config.setUsername("postgres");
            config.setPassword("Ven06246");
            config.setDriverClassName("org.postgresql.Driver");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000);
            
            // PostgreSQL-specific optimizations
            config.addDataSourceProperty("preparedStatementCacheQueries", 250);
            config.addDataSourceProperty("preparedStatementCacheSizeMiB", 5);
            config.addDataSourceProperty("socketTimeout", 30);
            
            dataSource = new HikariDataSource(config);
            
            // 3. Immediate connection test with version check
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT version(), current_user, current_database()")) {
                if (rs.next()) {
                    System.out.println("✅ Database Connection Successful!");
                    System.out.println("  PostgreSQL Version: " + rs.getString(1));
                    System.out.println("  Connected as: " + rs.getString(2));
                    System.out.println("  Database: " + rs.getString(3));
                }
            }
        } catch (Exception e) {
            throw new ServletException("Database initialization failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // Log all received parameters
        System.out.println("\n=== New Registration Attempt ===");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            System.out.println(name + ": " + request.getParameter(name));
        }

        // Get form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Clean and validate input
        phone = phone != null ? phone.replaceAll("[^0-9]", "") : "";
        
        if (!isValidInput(firstName, lastName, username, email, phone, password, confirmPassword)) {
            request.setAttribute("error", "Invalid input. Please check your details.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            
            // 1. Check for existing user with detailed logging
            if (userExists(conn, email, username)) {
                System.out.println("❌ User already exists (email: " + email + ", username: " + username + ")");
                request.setAttribute("error", "Email or username already exists.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // 2. Hash password
            String hashedPassword = hashPassword(password);
            System.out.println("🔑 Password hashed successfully");

            // 3. Insert new user with detailed logging
            System.out.println("🔄 Attempting to insert new user...");
            if (createUser(conn, firstName, lastName, username, email, phone, hashedPassword)) {
                conn.commit();
                System.out.println("✅ User successfully registered: " + username);
                request.setAttribute("message", "Registration successful!");
                response.sendRedirect("login.jsp");
            } else {
                conn.rollback();
                System.out.println("❌ Failed to insert user");
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.err.println("⚠️ Database error during registration:");
            e.printStackTrace();
            throw new ServletException("Database error during registration", e);
        } finally {
            out.close();
        }
    }

    // Helper methods with enhanced logging
    private boolean isValidInput(String firstName, String lastName, String username, 
                               String email, String phone, String password, String confirmPassword) {
        boolean valid = firstName != null && !firstName.isEmpty() &&
                       lastName != null && !lastName.isEmpty() &&
                       username != null && !username.isEmpty() &&
                       isValidEmail(email) &&
                       isValidPhone(phone) &&
                       isStrongPassword(password) &&
                       password.equals(confirmPassword);
        
        if (!valid) {
            System.out.println("❌ Validation failed for:");
            if (firstName == null || firstName.isEmpty()) System.out.println("  - First name missing");
            if (lastName == null || lastName.isEmpty()) System.out.println("  - Last name missing");
            if (username == null || username.isEmpty()) System.out.println("  - Username missing");
            if (!isValidEmail(email)) System.out.println("  - Invalid email: " + email);
            if (!isValidPhone(phone)) System.out.println("  - Invalid phone: " + phone);
            if (!isStrongPassword(password)) System.out.println("  - Weak password");
            if (!password.equals(confirmPassword)) System.out.println("  - Passwords don't match");
        }
        return valid;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private boolean isStrongPassword(String password) {
        return password != null && password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*].*");
    }

    private boolean userExists(Connection conn, String email, String username) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE email = ? OR username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean createUser(Connection conn, String firstName, String lastName, 
                             String username, String email, String phone, 
                             String hashedPassword) throws SQLException {
        String sql = "INSERT INTO students (first_name, last_name, username, email, phone, password_hash) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, username);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, hashedPassword);
            
            int rowsAffected = stmt.executeUpdate();
            
            // Get generated keys (user_id)
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println("  Generated user_id: " + rs.getInt(1));
                }
            }
            
            return rowsAffected == 1;
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    @Override
    public void destroy() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("Database connection pool closed");
        }
    }
}