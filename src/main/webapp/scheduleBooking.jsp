<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%@ page session="true" %>
<%
    // Check if the user is logged in
    String studentName = (String) session.getAttribute("studentName");
    if (studentName == null) {
       response.sendRedirect("login.jsp");
       return;
    }

    // Get the category name from the URL parameter
    String category = request.getParameter("category");
    if (category == null || category.isEmpty()) {
        category = "a wellness session"; // Default if no category is provided
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Schedule Your Session - BC Student Wellness</title>
    <link rel="stylesheet" href="css/scheduleBooking.css"> <%-- This line links to your main CSS file --%>
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
        <div class="container schedule-booking-area">
            <h1>Schedule Your <%= category %> Session</h1>
            <p class="intro-text">
                Please select your preferred date and time, and provide any additional notes for your session.
            </p>

            <hr class="separator">

            <form action="ScheduleBookingServlet" method="post" class="booking-form">
                <input type="hidden" name="category" value="<%= category %>">

                <div class="form-group">
                    <label for="bookingDate">Preferred Date:</label>
                    <input type="date" id="bookingDate" name="bookingDate" required min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                </div>

                <div class="form-group">
                    <label>Available Time Slots:</label>
                    <div class="time-slots-grid">
                        <label>
                            <input type="radio" name="timeSlot" value="09:00 AM" required>
                            <span>09:00 AM</span>
                        </label>
                        <label>
                            <input type="radio" name="timeSlot" value="10:00 AM">
                            <span>10:00 AM</span>
                        </label>
                        <label>
                            <input type="radio" name="timeSlot" value="11:00 AM">
                            <span>11:00 AM</span>
                        </label>
                        <label>
                            <input type="radio" name="timeSlot" value="01:00 PM">
                            <span>01:00 PM</span>
                        </label>
                        <label>
                            <input type="radio" name="timeSlot" value="02:00 PM">
                            <span>02:00 PM</span>
                        </label>
                        <label>
                            <input type="radio" name="timeSlot" value="03:00 PM">
                            <span>03:00 PM</span>
                        </label>
                        <label>
                            <input type="radio" name="timeSlot" value="04:00 PM">
                            <span>04:00 PM</span>
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="counselorPreference">Counselor Preference (Optional):</label>
                    <select id="counselorPreference" name="counselorPreference">
                        <option value="">Any Available Counselor</option>
                        <option value="Dr. Emily White">Dr. Emily White</option>
                        <option value="Mr. David Lee">Mr. David Lee</option>
                        <option value="Ms. Sarah Chen">Ms. Sarah Chen</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="notes">Additional Notes (Optional):</label>
                    <textarea id="notes" name="notes" rows="4" placeholder="e.g., Specific areas you'd like to discuss, preferred communication method for follow-up, etc."></textarea>
                </div>

                <button type="submit" class="button-small">Confirm Booking</button>
                <a href="bookSession.jsp" class="button-small" style="background: var(--primary-gradient);">Cancel and Go Back</a>
            </form>

            <hr class="separator">

            <p class="final-note">
                Your booking request will be reviewed by our team. You will receive a confirmation shortly.
            </p>

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