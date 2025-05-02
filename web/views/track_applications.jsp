<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Track My Applications</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4 text-center">📄 My Internship Applications</h2>

    <form method="get" action="TrackApplicationsServlet" class="row g-3 align-items-center mb-4">
        <div class="col-md-4">
            <label class="form-label">Filter by Status</label>
            <select name="status" class="form-select">
                <option value="all">All</option>
                <option value="Pending">Pending</option>
                <option value="Accepted">Accepted</option>
                <option value="Rejected">Rejected</option>
            </select>
        </div>

        <div class="col-md-4">
            <label class="form-label">Sort by Date</label>
            <select name="sort" class="form-select">
                <option value="desc">Newest First</option>
                <option value="asc">Oldest First</option>
            </select>
        </div>

        <div class="col-md-4 mt-4">
            <button type="submit" class="btn btn-primary w-100 mt-md-2">Apply</button>
        </div>
    </form>

    <%
        List<Map<String, String>> applications = (List<Map<String, String>>) request.getAttribute("applications");

        if (applications == null || applications.isEmpty()) {
    %>
        <div class="alert alert-info text-center">No applications found.</div>
    <%
        } else {
    %>
        <div class="table-responsive">
        <table class="table table-striped table-hover table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Internship</th>
                    <th>Company</th>
                    <th>Status</th>
                    <th>Applied At</th>
                    <th>CV</th>
                    <th>Transcript</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Map<String, String> app : applications) {
            %>
                <tr>
                    <td><%= app.get("internshipTitle") %></td>
                    <td><%= app.get("companyName") %></td>
                    <td>
                        <span class="badge bg-<%= "Accepted".equals(app.get("status")) ? "success" : 
                                                 "Rejected".equals(app.get("status")) ? "danger" : "warning text-dark" %>">
                            <%= app.get("status") %>
                        </span>
                    </td>
                    <td><%= app.get("appliedAt") %></td>
                    <td><a href="<%= request.getContextPath() %>/uploads/<%= app.get("cvPath") %>" target="_blank" class="btn btn-sm btn-outline-primary">View CV</a></td>
                    <td><a href="<%= request.getContextPath() %>/uploads/<%= app.get("transcriptPath") %>" target="_blank" class="btn btn-sm btn-outline-secondary">View Transcript</a></td>
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

    <div class="text-center mt-4">
        <a href="<%= request.getContextPath() %>/views/dashboard_student.jsp" class="btn btn-outline-secondary btn-sm">⬅ Back to Dashboard</a>
    </div>
</div>
</body>
</html>
