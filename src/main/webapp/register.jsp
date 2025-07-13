<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/register.css">
    <title>BC Student Wellness - Registration</title>

</head>
<body>
        <div class="registration-container">
            <div class="header">
                <div class="wellness-icon">
                    <i class="fas fa-heartbeat"></i>
                </div>
                <h1>Join BC Wellness</h1>
                <p>Create your account to get started</p>
            </div>

            <form id="registrationForm" novalidate>
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <i class="fas fa-user input-icon"></i>
                    <input type="text" id="firstName" name="firstName" required/>
                    <div class="validation-message" id="firstNameError"></div>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <i class="fas fa-user input-icon"></i>
                    <input type="text" id="lastName" name="lastName" required/>
                    <div class="validation-message" id="lastNameError"></div>
                </div>

                <div class="form-group">
                    <label for="username">Username</label>
                    <i class="fas fa-at input-icon"></i>
                    <input type="text" id="username" name="username" required/>
                    <div class="validation-message" id="usernameError"></div>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <i class="fas fa-envelope input-icon"></i>
                    <input type="email" id="email" name="email" required/>
                    <div class="validation-message" id="emailError"></div>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <i class="fas fa-phone input-icon"></i>
                    <input type="tel" id="phone" name="phone" placeholder="(123) 456-7890" required/>
                    <div class="validation-message" id="phoneError"></div>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <i class="fas fa-lock input-icon"></i>
                    <input type="password" id="password" name="password" required/>
                    <i class="fas fa-eye password-toggle" id="passwordToggle"></i>
                    <div class="validation-message" id="passwordError"></div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <i class="fas fa-lock input-icon"></i>
                    <input type="password" id="confirmPassword" name="confirmPassword" required/>
                    <i class="fas fa-eye password-toggle" id="confirmPasswordToggle"></i>
                    <div class="validation-message" id="confirmPasswordError"></div>
                </div>

                <button type="submit" class="submit-btn" id="submitBtn">
                    <i class="fas fa-user-plus"></i> Create Account
                </button>
            </form>

            <div class="login-link">
                Already have an account? <a href="login.jsp">Sign In</a>
            </div>
        </div>
</body>
</html>