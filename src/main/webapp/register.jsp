<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/register.css">
    <title>BC Student Wellness - Registration</title>

</head>
<body>
    <div class="registration-container">
        <div class="header">
            <h1>Join BC Wellness</h1>
            <p>Create your account to get started</p>
        </div>

        <div id="alertContainer"></div>

        <form id="registrationForm" novalidate>
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" required>
                <div class="validation-message" id="firstNameError"></div>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" required>
                <div class="validation-message" id="lastNameError"></div>
            </div>

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>
                <div class="validation-message" id="usernameError"></div>
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required>
                <div class="validation-message" id="emailError"></div>
            </div>

            <div class="form-group">
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" placeholder="(123) 456-7890" required>
                <div class="validation-message" id="phoneError"></div>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>


            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <div class="validation-message" id="confirmPasswordError"></div>
            </div>

            <button type="submit" class="submit-btn" id="submitBtn">Create Account</button>
        </form>

        <div class="login-link">
            Already have an account? <a href="login.jsp">Sign In</a>
        </div>
    </div>


</body>
</html>