<%-- 
    Document   : Register
    Created on : Mar 10, 2024, 11:34:15 AM
    Author     : HELLO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login/Sign Up</title>
        <link href="css/style.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f2f2f2;
            }

            .container {
                width: 400px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .form-container {
                padding: 20px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
            }

            input[type="text"],
            input[type="password"] {
                width: 94%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            button {
                width: 100%;
                padding: 10px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            button:hover {
                background-color: #0056b3;
            }

            p {
                margin-top: 15px;
            }

            #signupForm {
                display: none;
            }

            #signupLink, #loginLink {
                color: #007bff;
                text-decoration: none;
            }

            #signupLink:hover, #loginLink:hover {
                text-decoration: underline;
            }

            .body {
                background-attachment: fixed;
            }


        </style>
    </head>
    <body class="hero-header body">
        <div class="container" style="margin-bottom: 300px">
            <div class="form-container">
                <form id="loginForm" action="LoginController" method="post">
                    <h2>Login</h2>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value="${requestScope.username}" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" value="${requestScope.password}" maxlength="32" minlength="6" required>
                    </div>
                    <button type="submit" >Login</button>
                    <input type="checkbox" name="rememberme" id="remember" value="rem"> Remember me <a style="margin-left: 100px;" href="#" id="forgetLink">Forget password</a>
                    <p>Don't have an account? <a href="#" id="signupLink">Sign Up</a></p>
                    <div style="color: red">
                        <c:if test = "${requestScope.wrong ne null}">
                            <c:out value="${requestScope.wrong}"></c:out>
                        </c:if>
                    </div>
                    <div style="color: green">
                        <c:if test = "${requestScope.success ne null}">
                            <c:out value="${requestScope.success}"></c:out>
                        </c:if>
                    </div>
                </form>



                <form id="forgetForm" style="display:none;" action="ForgetPasswordServlet" method="post">
                    <h2>Forget password</h2>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Phone number</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" required>
                    </div>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    <button type="submit">Change password</button>
                    <p>Already have an account? <a href="#" id="loginLink">Login</a></p>
                </form>
                <div style="color: red">
                    <c:if test = "${requestScope.error ne null}">
                        <c:out value="${requestScope.error}"></c:out>
                    </c:if>
                </div>


                <form id="signupForm" style="display:none;" action="SignUpController" method="post">
                    <h2>Sign Up</h2>
                    <div class="form-group">
                        <label for="fullName">Full name</label>
                        <input type="text" id="fullName" name="fullName" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Phone number</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" required>
                    </div>
                    <div class="form-group">
                        <label for="newUsername">Username</label>
                        <input type="text" id="newUsername" name="newUsername" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">Password</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="rePassword">Re Password</label>
                        <input type="password" id="rePassword" name="rePassword" required>
                    </div>
                    <button type="submit">Sign Up</button>
                    <p>Already have an account? <a href="#" id="backToLoginLink">Login</a></p>
                </form>
            </div>
        </div>


        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const loginForm = document.getElementById('loginForm');
                const signupForm = document.getElementById('signupForm');
                const forgetForm = document.getElementById('forgetForm');

                const loginLink = document.getElementById('loginLink');
                const signupLink = document.getElementById('signupLink');
                const forgetLink = document.getElementById('forgetLink');
                const backToLoginLink = document.getElementById('backToLoginLink')

                var displayForm = "${not empty requestScope.displayForm ? requestScope.displayForm : ''}";

                if (displayForm === '') {
                    // Nếu displayForm là rỗng, hiển thị form đăng nhập và ẩn các form khác
                    loginForm.style.display = 'block';
                    signupForm.style.display = 'none';
                    forgetForm.style.display = 'none';

                    loginLink.addEventListener('click', function (e) {
                        e.preventDefault();
                        loginForm.style.display = 'block';
                        signupForm.style.display = 'none';
                        forgetForm.style.display = 'none';
                    });

                    signupLink.addEventListener('click', function (e) {
                        e.preventDefault();
                        loginForm.style.display = 'none';
                        signupForm.style.display = 'block';
                        forgetForm.style.display = 'none';
                    });

                    forgetLink.addEventListener('click', function (e) {
                        e.preventDefault();
                        loginForm.style.display = 'none';
                        signupForm.style.display = 'none';
                        forgetForm.style.display = 'block';
                    });

                    backToLoginLink.addEventListener('click', function (e) {
                        e.preventDefault();
                        loginForm.style.display = 'block';
                        signupForm.style.display = 'none';
                        forgetForm.style.display = 'none';
                    });
                } else {
                    // Nếu displayForm không rỗng, hiển thị form tương ứng và ẩn các form khác
                    switch (displayForm) {
                        case "signupForm":
                            signupForm.style.display = 'block';
                            loginForm.style.display = 'none';
                            forgetForm.style.display = 'none';
                            loginLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'block';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'none';
                            });

                            signupLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'none';
                                signupForm.style.display = 'block';
                                forgetForm.style.display = 'none';
                            });

                            forgetLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'none';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'block';
                            });

                            backToLoginLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'block';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'none';
                            });
                            break;
                        case "forgetForm":
                            forgetForm.style.display = 'block';
                            loginForm.style.display = 'none';
                            signupForm.style.display = 'none';
                            loginLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'block';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'none';
                            });

                            signupLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'none';
                                signupForm.style.display = 'block';
                                forgetForm.style.display = 'none';
                            });

                            forgetLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'none';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'block';
                            });

                            backToLoginLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'block';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'none';
                            });
                            break;
                        default:
                            loginForm.style.display = 'block';
                            signupForm.style.display = 'none';
                            forgetForm.style.display = 'none';
                            loginLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'block';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'none';
                            });

                            signupLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'none';
                                signupForm.style.display = 'block';
                                forgetForm.style.display = 'none';
                            });

                            forgetLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'none';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'block';
                            });

                            backToLoginLink.addEventListener('click', function (e) {
                                e.preventDefault();
                                loginForm.style.display = 'block';
                                signupForm.style.display = 'none';
                                forgetForm.style.display = 'none';
                            });
                    }
                    displayForm = '';
                }
            });
        </script>
<script>
        document.addEventListener("DOMContentLoaded", function() {
            var usernameInput = document.querySelector('input[name="username"]');
            var rememberMeCheckbox = document.getElementById("remember");

            if (usernameInput.value.trim() !== "") {
                rememberMeCheckbox.checked = true;
            }
        });
    </script>

    </body>
</html>
