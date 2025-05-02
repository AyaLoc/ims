<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession, model.StudentMailBean" %>
<%
    if (session == null || !"student".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    int studentId = (int) session.getAttribute("userId");
    StudentMailBean mailBean = new StudentMailBean();
    int unreadEmails = mailBean.getUnreadEmailCount(studentId);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="mb-4 text-center">🎓 Student Dashboard</h2>
        <div class="card p-4 shadow">
            <p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>
            <ul class="list-group">
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/BrowseInternshipsServlet">📌 Browse Internships</a></li>
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/TrackApplicationsServlet">📄 Track My Applications</a></li>
                <li class="list-group-item"><a href="<%=request.getContextPath()%>/SubmitReviewServlet">📝 Submit Internship Review</a></li>
                <li class="list-group-item">
                    <a href="<%=request.getContextPath()%>/StudentMailboxServlet">📬 Inbox
                        <% if (unreadEmails > 0) { %>
                            <span class="badge bg-danger"><%= unreadEmails %></span>
                        <% } %>
                    </a>
                </li>
            </ul>
            <div class="mt-3">
                <a href="<%= request.getContextPath() %>/LogoutServlet" class="btn btn-outline-secondary btn-sm">Logout</a>
            </div>
        </div>
    </div>
</body>
</html>
