<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login | Royal Hotel</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            body {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                background: linear-gradient(45deg, #f3f4f6 50%, #ffffff 50%);
            }

            .login-container {
                background: white;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            .login-title {
                font-size: 24px;
                color: #333;
                margin-bottom: 30px;
                text-align: center;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                color: #666;
                font-size: 14px;
            }

            .form-group input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 14px;
                transition: border-color 0.3s ease;
            }

            .form-group input:focus {
                outline: none;
                border-color: #6366f1;
            }

            .remember-forgot {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .remember-me {
                display: flex;
                align-items: center;
                gap: 5px;
                font-size: 14px;
                color: #666;
            }

            .forgot-password {
                color: #6366f1;
                text-decoration: none;
                font-size: 14px;
            }

            .forgot-password:hover {
                text-decoration: underline;
            }

            .login-button {
                width: 100%;
                padding: 12px;
                background-color: #6366f1;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .login-button:hover {
                background-color: #4f46e5;
            }

            .social-login {
                margin-top: 20px;
                text-align: center;
            }

            .social-text {
                color: #666;
                font-size: 14px;
                margin-bottom: 15px;
            }

            .social-icons {
                display: flex;
                justify-content: center;
                gap: 10px;
            }

            .social-icon {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f3f4f6;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            .social-icon.facebook {
                color: #1877f2;
            }

            .social-icon.twitter {
                color: #1da1f2;
            }

            .social-icon:hover {
                background-color: #e5e7eb;
            }
        </style>
        <!-- Font Awesome for social icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <div class="login-container">
            <h1 class="login-title">Login ${role}</h1>
            <form action="../${role}/login" method="POST">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="remember-forgot">
                    <label class="remember-me">
                        <input type="checkbox" name="remember">
                        Remember me
                    </label>
                    <a href="../requestPassword" class="forgot-password">Forgot Password?</a>
                </div>
                <button type="submit" class="login-button">LOGIN</button>
                <span style="display: block;text-align: center; margin-top: 5px; color: red">${errorMessage}</span>
            </form>
        </div>
    </body>
</html>