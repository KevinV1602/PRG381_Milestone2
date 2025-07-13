package com.group_P1.wellness.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ResourceServlet")
@SuppressWarnings({"unused", "java:S112"})
public class ResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/resources.jsp").forward(request, response);
        System.out.println("Resources page accessed by: " + request.getRemoteUser());
        HttpSession session = request.getSession(false); // don’t create new session
if (session == null || session.getAttribute("studentName") == null) {
    response.sendRedirect("login.jsp"); // user not logged in
    return; // stop further execution
}

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
        
    }
    
}