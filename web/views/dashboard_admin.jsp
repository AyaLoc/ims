<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
    if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="mb-4 text-center">🛡️ Admin Panel</h2>
        <div class="card p-4 shadow">
            <ul class="list-group">
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/AdminUserManagementServlet">👥 Manage User Accounts</a></li>
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/ModerateReviewsServlet">📝 Moderate Reviews</a></li>
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/MonitorSystemServlet">📊 Monitor System Activities</a></li>
            </ul>
            <div class="mt-3">
                <a href="<%=request.getContextPath()%>/LogoutServlet" class="btn btn-outline-danger btn-sm">Logout</a>
            </div>
        </div>
    </div>
</body>
</html>
