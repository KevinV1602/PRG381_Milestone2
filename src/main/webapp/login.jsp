<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>

    <div class="login-container">
        <div class="login-header">
            <div class="wellness-icon">
                <i class="fas fa-heartbeat"></i>
            </div>
            <h2>Welcome Back</h2>
            <p>Sign in to your wellness journey</p>
        </div>

        <form action="LoginServlet" method="post" class="login-form">
            <div class="form-section">
                <h3 class="form-section-title">Account Information</h3>

                <div class="form-group">
                    <label for="username" class="form-label">Username or Email</label>
                    <i class="fas fa-user input-icon"></i>
                    <input type="text" id="username" name="username" class="form-control" placeholder="Enter your username or email" required />
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">Password</label>
                    <i class="fas fa-lock input-icon"></i>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Enter your password" required />
                    <div class="password-toggle" onclick="togglePassword()">
                        <i class="fas fa-eye" id="password-toggle-icon"></i>
                    </div>
                </div>
            </div>

            <button type="submit" class="login-btn">
                <i class="fas fa-sign-in-alt"></i> Sign In to Wellness System
            </button>
        </form>

        <%
            String error = request.getParameter("error");
            if ("invalid".equals(error)) {
        %>
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-triangle"></i>
                Invalid username or password. Please try again.
            </div>
        <% } else if ("exception".equals(error)) { %>
            <div class="alert alert-warning">
                <i class="fas fa-exclamation-circle"></i>
                Something went wrong. Please try again later.
            </div>
        <% } %>
    </div>
</body>
</html>

