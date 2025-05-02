<%@ page contentType="text/html; charset=UTF-8" import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Reviews</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        .table th, .table td {
            vertical-align: middle;
        }
        .reply-box textarea {
            width: 100%;
        }
    </style>
</head>
<body class="bg-light">
<div class="container py-5">
    <h2 class="mb-4 text-center">📝 Reviews for Your Internships</h2>

    <%
        List<Map<String, String>> reviews = (List<Map<String, String>>) request.getAttribute("reviews");

        if (reviews == null || reviews.isEmpty()) {
    %>
        <div class="alert alert-info text-center">No reviews submitted yet.</div>
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
                        <th>Submitted</th>
                        <th>Reply</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Map<String, String> r : reviews) {
                        String replyComment = r.get("replyComment");
                %>
                    <tr>
                        <td><%= r.get("studentName") %></td>
                        <td><%= r.get("internshipTitle") %></td>
                        <td><span class="text-warning"><%= r.get("rating") %> ★</span></td>
                        <td><%= r.get("comment") %></td>
                        <td><%= r.get("submissionDate") %></td>
                        <td>
                            <% if (replyComment != null && !replyComment.trim().isEmpty()) { %>
                                <div class="alert alert-success mb-0">
                                    <strong>✅ Replied:</strong><br>
                                    <em><%= replyComment %></em>
                                </div>
                            <% } else { %>
                                <form method="post" action="<%= request.getContextPath() %>/ReviewReplyServlet" class="reply-box">
                                    <input type="hidden" name="reviewId" value="<%= r.get("reviewId") %>"/>
                                    <textarea name="reply" rows="3" class="form-control mb-2" placeholder="Enter your reply..." required></textarea>
                                    <button type="submit" class="btn btn-sm btn-primary">Submit Reply</button>
                                </form>
                            <% } %>
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
        <a href="views/dashboard_employer.jsp" class="btn btn-secondary">⬅ Back to Dashboard</a>
    </div>
</div>
</body>
</html>
