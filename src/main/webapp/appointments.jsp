<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%-- You would likely have an Appointment class/bean from your backend for better structure --%>
<%-- For example: <%@ page import="com.belgiumcampus.wellness.model.Appointment" %> --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Appointments - BC Student Wellness System</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
    <link href="css/appointment.css" rel="stylesheet">

    </head>
<body>

    <%
        // **SERVER-SIDE LOGIC (usually done in a Servlet, then passed as request attributes)**
        // For demonstration purposes directly in JSP:
        String studentName = (String) session.getAttribute("studentName");
        String studentNumber = (String) session.getAttribute("studentNumber"); // Assuming you store student number

        // If session is invalid, redirect to login
        if (studentName == null || studentNumber == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // --- Simulate fetching appointments from a database ---
        // In a real scenario, a Servlet would fetch this data based on studentNumber
        // and set it as a request attribute: request.setAttribute("myAppointments", appointmentList);
        List<String[]> myAppointments = new java.util.ArrayList<>();
        if ("S123456".equals(studentNumber)) { // Example: If specific student logged in
            myAppointments.add(new String[]{"1", "Dr. Emily Green", "Therapy", "2025-07-20 10:00", "Confirmed"});
            myAppointments.add(new String[]{"2", "Dr. Ben Carter", "Career Guidance", "2025-07-25 14:30", "Pending"});
            myAppointments.add(new String[]{"3", "Dr. Sarah Jones", "Stress Management", "2025-07-10 11:00", "Completed"});
        } else { // For other students, maybe no appointments or different ones
            // myAppointments.add(new String[]{"4", "Dr. Alex Lee", "Nutrition", "2025-08-01 09:00", "Confirmed"});
        }
        // ----------------------------------------------------
    %>

    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0">Welcome, <%= studentName %>!</h2>
            <form action="LogoutServlet" method="post" style="display:inline;">
                <button type="submit" class="btn btn-danger btn-sm">Logout</button>
            </form>
        </div>

        <ul class="nav-tabs mb-4">
            <li class="nav-item">
                <a class="nav-link" href="dashboard.jsp">Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="myAppointments.jsp">My Appointments</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Wellness Info</a>
            </li>
        </ul>

        <div class="card shadow-sm">
            <div class="card-body">
                <div class="page-header">
                    <h1 class="display-5">Your Scheduled Appointments</h1>
                    <p class="lead text-muted">View your upcoming and past wellness service sessions.</p>
                </div>

                <div class="appointments-table-container">
                    <% if (myAppointments.isEmpty()) { %>
                        <div class="alert alert-info empty-state text-center" role="alert">
                            <h4 class="alert-heading">No Appointments Found!</h4>
                            <p>It looks like you don't have any appointments scheduled yet.</p>
                            <hr>
                            <p class="mb-0">Please use the desktop application to book new wellness services.</p>
                        </div>
                    <% } else { %>
                        <table class="table table-hover table-striped appointments-table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Counselor</th>
                                    <th scope="col">Specialization</th>
                                    <th scope="col">Date & Time</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");
                                    for (String[] appt : myAppointments) {
                                %>
                                <tr>
                                    <th scope="row"><%= appt[0] %></th>
                                    <td><%= appt[1] %></td>
                                    <td><%= appt[2] %></td>
                                    <td><%= LocalDateTime.parse(appt[3].replace(" ", "T")).format(formatter) %></td>
                                    <td>
                                        <%
                                            String status = appt[4];
                                            String badgeClass = "";
                                            if ("Confirmed".equals(status)) {
                                                badgeClass = "badge-success";
                                            } else if ("Pending".equals(status)) {
                                                badgeClass = "badge-warning"; // Use warning for pending
                                            } else if ("Cancelled".equals(status)) {
                                                badgeClass = "badge-danger";
                                            } else if ("Completed".equals(status)) {
                                                badgeClass = "badge-info"; // Use info for completed
                                            } else {
                                                badgeClass = "badge-secondary"; // Default
                                            }
                                        %>
                                        <span class="badge <%= badgeClass %>"><%= status %></span>
                                    </td>
                                    <td>
                                        <button class="btn btn-info btn-sm" disabled style="opacity: 0.7;">View Details</button>
                                        <%-- For Milestone 1, actions like "Cancel" or "Reschedule" would involve backend logic,
                                             so it might be simpler to disable or omit them for now if not required.
                                        <form action="CancelAppointmentServlet" method="post" style="display:inline; margin-left: 10px;">
                                            <input type="hidden" name="appointmentId" value="<%= appt[0] %>">
                                            <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                        </form>
                                        --%>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    </body>
</html>