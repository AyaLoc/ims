<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Applications</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        .download-icon {
            margin-left: 6px;
            font-size: 18px;
            color: #0d6efd;
            text-decoration: none;
        }
        .download-icon:hover {
            color: #0a58ca;
        }
        .table-container {
            margin-top: 30px;
        }
    </style>
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="mb-4 text-center">📂 Manage Applications</h2>

    <%
        List<Map<String, String>> applications = (List<Map<String, String>>) request.getAttribute("applications");

        if (applications == null || applications.isEmpty()) {
    %>
        <div class="alert alert-info text-center">No applications found.</div>
    <%
        } else {
    %>
        <div class="table-responsive table-container">
            <table class="table table-bordered table-striped table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Student</th>
                        <th>Internship</th>
                        <th>Applied At</th>
                        <th>Status</th>
                        <th>CV</th>
                        <th>Transcript</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Map<String, String> app : applications) {
                            String cvPath = app.get("cvPath");
                            String transcriptPath = app.get("transcriptPath");
                    %>
                    <tr>
                        <td><%= app.get("studentName") %></td>
                        <td><%= app.get("internshipTitle") %></td>
                        <td><%= app.get("applied_date") %></td>
                        <td>
                            <% String status = app.get("status"); %>
                            <span class="badge bg-<%= 
                                "Accepted".equals(status) ? "success" :
                                "Rejected".equals(status) ? "danger" :
                                "warning" %>">
                                <%= status %>
                            </span>
                        </td>
                        <td>
                            <a href="<%= request.getContextPath() %>/uploads/<%= cvPath %>" target="_blank">Preview</a>
                            <a class="download-icon" href="<%= request.getContextPath() %>/uploads/<%= cvPath %>" download><i class="bi bi-download"></i>💾</a>
                        </td>
                        <td>
                            <a href="<%= request.getContextPath() %>/uploads/<%= transcriptPath %>" target="_blank">Preview</a>
                            <a class="download-icon" href="<%= request.getContextPath() %>/uploads/<%= transcriptPath %>" download><i class="bi bi-download"></i>💾</a>
                        </td>
                        <td>
                            <form method="post" action="ManageApplicationsServlet" class="d-flex gap-2">
                                <input type="hidden" name="appId" value="<%= app.get("id") %>"/>
                                <button type="submit" name="action" value="Accepted" class="btn btn-sm btn-success">✅ Accept</button>
                                <button type="submit" name="action" value="Rejected" class="btn btn-sm btn-danger">❌ Reject</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    <%
        }
    %>

    <div class="mt-4 text-center">
        <a href="views/dashboard_employer.jsp" class="btn btn-outline-secondary">⬅ Back to Dashboard</a>
    </div>
</div>

</body>
</html>
