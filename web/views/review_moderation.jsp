<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Moderate Reviews</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body class="bg-light">
<div class="container py-5">
    <h2 class="text-center mb-4">🛡️ Moderate Reviews</h2>

    <%
        List<Map<String, String>> reviews = (List<Map<String, String>>) request.getAttribute("reviews");

        if (reviews == null || reviews.isEmpty()) {
    %>
        <div class="alert alert-info">No reviews submitted yet.</div>
    <%
        } else {
    %>
    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Student</th>
                    <th>Internship</th>
                    <th>Rating</th>
                    <th>Comment</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Map<String, String> r : reviews) {
                    boolean isApproved = "true".equalsIgnoreCase(r.get("approved"));
            %>
                <tr>
                    <td><%= r.get("studentName") %></td>
                    <td><%= r.get("internshipTitle") %></td>
                    <td><%= r.get("rating") %> ★</td>
                    <td><%= r.get("comment") %></td>
                    <td><%= r.get("date") %></td>
                    <td>
                        <span class="badge <%= isApproved ? "bg-success" : "bg-warning text-dark" %>">
                            <%= isApproved ? "Approved" : "Pending" %>
                        </span>
                    </td>
                    <td>
                        <form method="post" action="ModerateReviewsServlet" class="d-flex gap-2">
                            <input type="hidden" name="reviewId" value="<%= r.get("id") %>"/>
                            <button type="submit" class="btn btn-sm btn-success" name="action" value="approve"
                                <%= isApproved ? "disabled" : "" %>>
                                ✅ Approve
                            </button>
                            <button type="submit" class="btn btn-sm btn-danger" name="action" value="delete"
                                onclick="return confirm('Are you sure you want to delete this review?')">
                                ❌ Delete
                            </button>
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
        <a href="views/dashboard_admin.jsp" class="btn btn-secondary">⬅ Back to Admin Panel</a>
    </div>
</div>
</body>
</html>
