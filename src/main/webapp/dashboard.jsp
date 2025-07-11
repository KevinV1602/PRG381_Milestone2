<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%@ page session="true" %>
<%


        String studentName = (String) session.getAttribute("studentName");

        if (studentName == null) {
           response.sendRedirect("login.jsp");
           return;
       }


%>
<%-- JSTL is NOT needed since you are using scriptlets for session management --%>
<%-- If you prefer JSTL for cleaner code later, you can replace the scriptlet with JSTL --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - BC Student Wellness</title>
    <link rel="stylesheet" href="css/style.css">
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
        <div class="container">
            <%-- Personalized Welcome Message using scriptlet --%>
            <h1>Welcome, <%= studentName %>!</h1>

            <p class="dashboard-intro">
                You are now logged in to the BC Student Wellness Management System.
                Use this dashboard to manage your wellness services.
            </p>

            <div class="dashboard-info-section">
                <h2>Quick Overview</h2>
                <p>
                    This section will eventually contain summaries of your appointments, feedback,
                    and other important information.
                </p>
                <ul>
                    <li><a href="#" class="quick-link">View My Appointments (Future)</a></li>
                    <li><a href="#" class="quick-link">Submit Feedback (Future)</a></li>
                    <li><a href="#" class="quick-link">Access Resources (Future)</a></li>
                </ul>
            </div>

            <%-- Optional: A larger logout button in the main content area for prominence --%>
            <div class="dashboard-actions">
                <p>Ready to log out?</p>
                <form action="LogoutServlet" method="post">
                    <input type="submit" value="Logout" class="button-big logout-button" />
                </form>
            </div>
        </div>
    </main>

    <footer class="main-footer">
        <div class="footer-content">
            <p>&copy; 2025 Belgium Campus. All rights reserved.</p>
            <p class="attribution-text">
                <img src="images/heart.png"  class="logo-img">
            </p>
        </div>
    </footer>
</body>
</html>