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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book a Session - BC Student Wellness</title>
    <link rel="stylesheet" href="css/makeBooking.css">
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
        <div class="container book-session-area">
            <h1>Book a Wellness Session</h1>
            <p class="intro-text">
                Explore our diverse range of wellness support options designed to help you thrive.
                Select a category that aligns with your current needs to find specific session types.
            </p>

            <hr class="separator">

            <%-- Wellness Pillar: Mental & Emotional Well-being --%>
            <div class="wellness-pillar">
                <h2 class="pillar-title"><img src="images/mental.png" alt="Mental Health Icon" class="pillar-icon"> Mental & Emotional Well-being</h2>
                <p class="pillar-description">Support for your thoughts, feelings, and overall psychological health.</p>

                <div class="wellness-category-grid">
                    <%-- Sub-category: Anxiety & Stress Management --%>
                    <div class="wellness-category-card">
                        <h3>Anxiety & Stress Management</h3>
                        <p>Learn coping strategies, relaxation techniques, and mindfulness practices to reduce anxiety and stress.</p>
                        <ul>
                            <li>Breathing Exercises</li>
                            <li>Stress Reduction Workshops</li>
                            <li>Mindfulness Coaching</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>

                    <%-- Sub-category: Depression & Mood Support --%>
                    <div class="wellness-category-card">
                        <h3>Depression & Mood Support</h3>
                        <p>Confidential counseling and support groups for navigating feelings of sadness, low mood, and depression.</p>
                        <ul>
                            <li>Individual Counseling</li>
                            <li>Group Therapy Sessions</li>
                            <li>Mood Management Workshops</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>

                    <%-- Sub-category: Emotional Resilience --%>
                    <div class="wellness-category-card">
                        <h3>Emotional Resilience</h3>
                        <p>Build inner strength and develop skills to bounce back from life's challenges and adapt to change.</p>
                        <ul>
                            <li>Resilience Building Workshops</li>
                            <li>Cognitive Behavioral Coaching</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>
                </div> <%-- End wellness-category-grid --%>
            </div> <%-- End wellness-pillar --%>

            <hr class="separator">

            <%-- Wellness Pillar: Physical Health & Nutrition --%>
            <div class="wellness-pillar">
                <h2 class="pillar-title"><img src="images/exercise.png" alt="Physical Health Icon" class="pillar-icon"> Physical Health & Nutrition</h2>
                <p class="pillar-description">Guidance for maintaining a healthy body, energy levels, and balanced diet.</p>

                <div class="wellness-category-grid">
                    <%-- Sub-category: Nutritional Guidance --%>
                    <div class="wellness-category-card">
                        <h3>Nutritional Guidance</h3>
                        <p>Personalized advice and resources for healthy eating habits, balanced diets, and understanding food's impact on well-being.</p>
                        <ul>
                            <li>One-on-One Nutrition Consults</li>
                            <li>Healthy Meal Prep Workshops</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>

                    <%-- Sub-category: Fitness & Activity --%>
                    <div class="wellness-category-card">
                        <h3>Fitness & Activity</h3>
                        <p>Explore ways to integrate physical activity into your routine, from quick breaks to structured exercise plans.</p>
                        <ul>
                            <li>Fitness Consultations</li>
                            <li>Campus Activity Guides</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>

                    <%-- Sub-category: Sleep & Rest --%>
                    <div class="wellness-category-card">
                        <h3>Sleep & Rest</h3>
                        <p>Learn strategies for improving sleep quality and ensuring adequate rest for optimal functioning.</p>
                        <ul>
                            <li>Sleep Hygiene Workshops</li>
                            <li>Relaxation Techniques for Sleep</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>
                </div> <%-- End wellness-category-grid --%>
            </div> <%-- End wellness-pillar --%>

            <hr class="separator">

            <%-- Wellness Pillar: Academic & Social Support --%>
            <div class="wellness-pillar">
                <h2 class="pillar-title"><img src="images/social.png" alt="Social-Academic Icon" class="pillar-icon"> Academic & Social Support</h2>
                <p class="pillar-description">Support for academic pressures, building connections, and navigating campus life.</p>

                <div class="wellness-category-grid">
                    <%-- Sub-category: Academic Stress & Time Management --%>
                    <div class="wellness-category-card">
                        <h3>Academic Stress & Time Management</h3>
                        <p>Workshops and coaching to help manage study-related stress, procrastination, and optimize your academic routine.</p>
                        <ul>
                            <li>Time Management Seminars</li>
                            <li>Study Skill Coaching</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>

                    <%-- Sub-category: Peer Connections & Social Skills --%>
                    <div class="wellness-category-card">
                        <h3>Peer Connections & Social Skills</h3>
                        <p>Opportunities to connect with fellow students and develop communication and relationship-building skills.</p>
                        <ul>
                            <li>Peer Support Groups</li>
                            <li>Social Confidence Workshops</li>
                        </ul>
                        <a href="#" class="button-small">Book Now</a>
                    </div>
                </div> <%-- End wellness-category-grid --%>
            </div> <%-- End wellness-pillar --%>

            <p class="intro-text" style="margin-top: 40px;">
                *Note: Actual booking functionality (selecting dates, times, specific counselors) would be developed in a future phase.*
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