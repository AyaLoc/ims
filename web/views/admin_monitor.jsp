<%@ page contentType="text/html;charset=UTF-8" import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>System Monitoring - Admin</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
<div class="container py-5">
    <h2 class="text-center mb-4">🔎 Admin System Monitoring</h2>

    <!-- stetistics section -->
    <div class="row text-center mb-5">
        <div class="col-md-4">
            <div class="card border-success">
                <div class="card-body">
                    <h5 class="card-title">📦 Total Internships</h5>
                    <p class="card-text display-6 text-success"><%= request.getAttribute("totalInternships") %></p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-primary">
                <div class="card-body">
                    <h5 class="card-title">📄 Total Applications</h5>
                    <p class="card-text display-6 text-primary"><%= request.getAttribute("totalApplications") %></p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-warning">
                <div class="card-body">
                    <h5 class="card-title">⏳ Pending Applications</h5>
                    <p class="card-text display-6 text-warning"><%= request.getAttribute("pendingApplications") %></p>
                </div>
            </div>
        </div>
    </div>

    <!-- internships section -->
    <h3 class="mb-3">📋 All Internships</h3>
    <%
        List<Map<String, String>> internships = (List<Map<String, String>>) request.getAttribute("internships");
        if (internships == null || internships.isEmpty()) {
    %>
        <div class="alert alert-info">No internships found.</div>
    <%
        } else {
    %>
        <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Company</th>
                    <th>Location</th>
                    <th>Date Posted</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Map<String, String> i : internships) {
            %>
                <tr>
                    <td><%= i.get("id") %></td>
                    <td><%= i.get("title") %></td>
                    <td><%= i.get("company") %></td>
                    <td><%= i.get("location") %></td>
                    <td><%= i.get("date") %></td>
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

    <!-- applications section -->
    <h3 class="mt-5 mb-3">📄 All Applications</h3>
    <%
        List<Map<String, String>> applications = (List<Map<String, String>>) request.getAttribute("applications");
        if (applications == null || applications.isEmpty()) {
    %>
        <div class="alert alert-info">No applications found.</div>
    <%
        } else {
    %>
        <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Student</th>
                    <th>Internship</th>
                    <th>Status</th>
                    <th>Applied Date</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Map<String, String> a : applications) {
            %>
                <tr>
                    <td><%= a.get("id") %></td>
                    <td><%= a.get("student") %></td>
                    <td><%= a.get("internship") %></td>
                    <td><%= a.get("status") %></td>
                    <td><%= a.get("date") %></td>
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
        <a href="views/dashboard_admin.jsp" class="btn btn-secondary">⬅ Back to Admin Dashboard</a>
    </div>
</div>
</body>
</html>
