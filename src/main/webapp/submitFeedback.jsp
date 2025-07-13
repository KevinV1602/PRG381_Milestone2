<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.*, jakarta.servlet.*" %> <%-- Using jakarta imports --%>
<%@ page session="true" %>
<%
    String studentName = (String) session.getAttribute("studentName");
    if (studentName == null) {
       response.sendRedirect("login.jsp");
       return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit Feedback - BC Student Wellness</title>
    <link rel="stylesheet" href="css/makeBooking.css"> <%-- Your main global styles --%>
    <link rel="stylesheet" href="css/submitFeedback.css"> <%-- NEW: Specific styles for this page --%>
    <link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>
    <header class="main-header">
        <div class="header-content">
            <a href="index.jsp" class="logo">
                <img src="images/search.png" alt="BC Wellness Logo" class="logo-img">
                <span class="logo-text">BC Wellness</span>
            </a>
            <nav class="main-nav">
                <ul>
                    <li><a href="dashboard.jsp" class="nav-button">Dashboard</a></li>
                    <%-- Logout button in Navbar --%>
                    <li>
                        <form action="LogoutServlet" method="post" class="nav-logout-form">
                            <input type="submit" value="Logout" class="nav-button nav-logout-btn" />
                        </form>
                    </li>
                </ul>
            </nav>
        </div>
    </header>

    <main class="main-content">
        <div class="container feedback-area">
            <h1>Submit Your Feedback</h1>
            <p class="intro-text">
                Your input helps us improve! Please share your experience with our wellness services.
            </p>

            <hr class="separator">

            <form action="SubmitFeedbackServlet" method="post" class="feedback-form">
                <div class="form-group">
                    <label for="feedbackType">Feedback Type:</label>
                    <select id="feedbackType" name="feedbackType" required>
                        <option value="">Select a type...</option>
                        <option value="general">General Comment</option>
                        <option value="session">Specific Session Feedback</option>
                        <option value="website">Website Experience</option>
                        <option value="suggestion">Suggestion for Improvement</option>
                        <option value="other">Other</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="rating">Rating (1-5, 5 being excellent):</label>
                    <input type="number" id="rating" name="rating" min="1" max="5" placeholder="e.g., 4" required>
                </div>

                <div class="form-group">
                    <label for="comments">Your Comments:</label>
                    <textarea id="comments" name="comments" rows="8" placeholder="Tell us about your experience..." required></textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="button-big">Submit Feedback</button>
                    <button type="reset" class="button-big button-secondary">Reset Form</button>
                </div>
            </form>
        </div>
    </main>

    <footer class="main-footer">
        <div class="footer-content">
            <p>&copy; 2025 Belgium Campus. All rights reserved.</p>
            <p class="attribution-text">
                <img src="images/heart.png" alt="Heart icon" class="logo-img">
            </p>
        </div>
    </footer>
</body>
</html>