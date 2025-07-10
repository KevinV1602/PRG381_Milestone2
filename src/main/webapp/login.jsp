<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5 text-center">
        <h2 class="text-primary mb-4">Login to Wellness System</h2>

        <form action="LoginServlet" method="post" class="w-50 mx-auto">
            <div class="mb-3">
                <input type="text" name="username" class="form-control" placeholder="Username" required />
            </div>
            <div class="mb-3">
                <input type="password" name="password" class="form-control" placeholder="Password" required />
            </div>
            <button type="submit" class="btn btn-success">Login</button>
        </form>

        <%
            String error = request.getParameter("error");
            if ("invalid".equals(error)) {
        %>
            <div class="alert alert-danger mt-4">Invalid username or password. Try again.</div>
        <% } else if ("exception".equals(error)) { %>
            <div class="alert alert-warning mt-4">Something went wrong. Please try later.</div>
        <% } %>
    </div>
</body>
</html>

