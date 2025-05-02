<%@ page contentType="text/html;charset=UTF-8" import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>📬 My Emails</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
<div class="container my-5">
    <h2 class="mb-4 text-center">📬 My Emails</h2>

    <%
        List<Map<String, String>> emails = (List<Map<String, String>>) request.getAttribute("emails");

        if (emails == null || emails.isEmpty()) {
    %>
        <div class="alert alert-info text-center">No emails received yet.</div>
    <%
        } else {
    %>
        <div class="row">
        <%
            for (Map<String, String> email : emails) {
        %>
            <div class="col-md-6 mb-4">
                <div class="card shadow-sm h-100">
                    <div class="card-header bg-primary text-white">
                        <strong><%= email.get("subject") %></strong>
                    </div>
                    <div class="card-body">
                        <p class="card-text"><%= email.get("body") %></p>
                    </div>
                    <div class="card-footer text-muted text-end">
                        <small>Sent: <%= email.get("sentDate") %></small>
                    </div>
                </div>
            </div>
        <%
            }
        %>
        </div>
    <%
        }
    %>

    <div class="text-center mt-4">
        <a href="views/dashboard_student.jsp" class="btn btn-secondary">⬅ Back to Dashboard</a>
    </div>
</div>
</body>
</html>
