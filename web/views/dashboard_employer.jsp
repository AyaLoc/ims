<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    if (!"employer".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Employer Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="mb-4 text-center">🏢 Employer Dashboard</h2>
        <div class="card p-4 shadow">
            <p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>
            <ul class="list-group">
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/views/manage_internships.jsp">📌 Manage Internships</a></li>
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/ManageApplicationsServlet">📂 Manage Applications</a></li>
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/ViewReviewsServlet">🗨️ Reply to Reviews</a></li>
            </ul>
            <div class="mt-3">
                <a href="<%=request.getContextPath()%>/LogoutServlet" class="btn btn-outline-secondary btn-sm">Logout</a>
            </div>
        </div>
    </div>
</body>
</html>
