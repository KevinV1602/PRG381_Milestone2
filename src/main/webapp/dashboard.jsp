<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%@ page session="true" %>
<%
    // Retrieve the logged-in student’s name from session
    String studentName = (String) session.getAttribute("studentName");

    if (studentName == null) {
        // If session expired or user not logged in, redirect to login page
        response.sendRedirect("login.jsp");
    }
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= studentName %>!</h2>

    <!-- Logout button -->
    <form action="LogoutServlet" method="post">
        <input type="submit" value="Logout" />
    </form>
</body>
</html>