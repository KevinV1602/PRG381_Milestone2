package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/FeedbackServlet")
@SuppressWarnings("unused") // Suppress "Class is never used" warning for IDE
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This currently just redirects to dashboard.jsp after a POST.
        // Actual feedback saving logic will go here later.
        response.sendRedirect("dashboard.jsp?feedback=success");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If accessed directly via GET, redirect to the feedback form page.
        response.sendRedirect("submitFeedback.jsp");
    }
}