<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.InternshipBean" %>
<%@ page session="true" %>
<%
    int empId = (int) session.getAttribute("userId");
    InternshipBean bean = new InternshipBean();
    List<InternshipBean> internships = bean.getAllByEmployer(empId);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Internships</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        body {
            background-color: #f0f4f8;
        }
        .container {
            max-width: 900px;
            margin: 30px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        textarea {
            resize: vertical;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-center mb-4">📋 Manage Internships</h2>

    <!-- Internship Form -->
    <form action="../PostInternshipServlet" method="post" class="mb-4">
        <h5 class="mb-3">➕ Post New Internship</h5>
        <div class="mb-3">
            <label class="form-label">Title:</label>
            <input type="text" class="form-control" name="title" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Company:</label>
            <input type="text" class="form-control" name="company_name" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Location:</label>
            <input type="text" class="form-control" name="location" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Description:</label>
            <textarea name="description" class="form-control" rows="4" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary w-100">📤 Post Internship</button>
    </form>

    <!-- posted internships -->
    <h5 class="mb-3">📌 Posted Internships</h5>
    <%
        if (internships == null || internships.isEmpty()) {
    %>
        <div class="alert alert-warning">No internships posted yet.</div>
    <%
        } else {
    %>
    <div class="table-responsive">
        <table class="table table-bordered table-striped align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Title</th>
                    <th>Company</th>
                    <th>Location</th>
                    <th>Posted</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (InternshipBean i : internships) { %>
                    <tr>
                        <td><%= i.getTitle() %></td>
                        <td><%= i.getCompanyName() %></td>
                        <td><%= i.getLocation() %></td>
                        <td><%= i.getPostedDate() %></td>
                        <td>
                            <div class="d-flex gap-2">
                                <a class="btn btn-sm btn-warning" href="<%=request.getContextPath()%>/EditInternshipServlet?id=<%= i.getId() %>">✏️ Edit</a>
                                <form method="post" action="../DeleteInternshipServlet" onsubmit="return confirm('Are you sure?')">
                                    <input type="hidden" name="id" value="<%= i.getId() %>" />
                                    <button type="submit" class="btn btn-sm btn-danger">🗑 Delete</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <% } %>

    <div class="mt-3 text-center">
        <a href="dashboard_employer.jsp" class="btn btn-link">⬅ Back to Dashboard</a>
    </div>
</div>

</body>
</html>
