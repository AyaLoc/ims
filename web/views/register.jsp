<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .register-card {
            max-width: 500px;
            margin: 60px auto;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.15);
        }
    </style>
</head>
<body>

<div class="container">
    <div class="register-card bg-white">
        <h3 class="text-center mb-4">👨‍🎓 Student Registration</h3>

        <form action="../RegisterServlet" method="post">
            <div class="mb-3">
                <label class="form-label">Username</label>
                <input name="username" class="form-control" required />
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input name="email" type="email" class="form-control" required />
            </div>
            <div class="mb-3">
                <label class="form-label">Password</label>
                <input name="password" type="password" class="form-control" required />
            </div>

            <button type="submit" class="btn btn-primary w-100">Register</button>
        </form>

        <div class="text-center mt-3">
            <a href="login.jsp" class="text-decoration-none">⬅ Back to Login</a>
        </div>
    </div>
</div>

</body>
</html>
