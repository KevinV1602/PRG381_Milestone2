package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Ensure that 'import jakarta.servlet.http.HttpSession;' IS NOT present here.
import java.io.IOException;

@WebServlet("/FeedbackServlet")
@SuppressWarnings({"unused", "java:S112", "RedundantThrows"}) // Added "RedundantThrows" for more specific suppression
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("dashboard.jsp?feedback=success");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("submitFeedback.jsp");
    }
}