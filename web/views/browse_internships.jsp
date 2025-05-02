<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List, model.InternshipBean" %>
<%@ page session="true" %>
<%
    List<InternshipBean> internships = (List<InternshipBean>) request.getAttribute("internships");
    int totalPages = (request.getAttribute("totalPages") != null)
                     ? (int) request.getAttribute("totalPages")
                     : 1;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Browse Internships</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">📋 Available Internships</h2>

    <% if (internships != null && !internships.isEmpty()) { %>
        <div class="row row-cols-1 row-cols-md-2 g-4">
        <% for (InternshipBean i : internships) { %>
            <div class="col">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title text-primary"><%= i.getTitle() %></h5>
                        <p class="card-text"><strong>🏢 Company:</strong> <%= i.getCompanyName() %></p>
                        <p class="card-text"><strong>📍 Location:</strong> <%= i.getLocation() %></p>
                        <p class="card-text"><strong>📅 Posted:</strong> <%= i.getPostedDate() %></p>
                        <p class="card-text"><strong>📝 Description:</strong><br> <%= i.getDescription() %></p>
                        <a href="<%= request.getContextPath() %>/views/apply.jsp?internshipId=<%= i.getId() %>" class="btn btn-success mt-2 w-100">Apply Now</a>
                    </div>
                </div>
            </div>
        <% } %>
        </div>
    <% } else { %>
        <div class="alert alert-info text-center mt-4">No internships available at the moment.</div>
    <% } %>

    <!-- pagination control -->
    <div class="text-center mt-4">
        <nav>
            <ul class="pagination justify-content-center">
                <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item">
                        <a class="page-link" href="BrowseInternshipsServlet?page=<%= i %>"><%= i %></a>
                    </li>
                <% } %>
            </ul>
        </nav>
    </div>

    <div class="text-center mt-3">
        <a href="<%= request.getContextPath() %>/views/dashboard_student.jsp" class="btn btn-outline-secondary btn-sm">⬅ Back to Dashboard</a>
    </div>
</div>
</body>
</html>
