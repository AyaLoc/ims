<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.*,java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Internship Review</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        body {
            background-color: #f5f7fa;
            font-family: 'Segoe UI', sans-serif;
        }
        .review-container {
            max-width: 600px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 12px rgba(0,0,0,0.1);
        }
        .stars label {
            font-size: 2rem;
            color: lightgray;
            cursor: pointer;
        }
        .stars input[type="radio"] {
            display: none;
        }
        .stars input[type="radio"]:checked ~ label,
        .stars label:hover,
        .stars label:hover ~ label {
            color: gold;
        }
    </style>
</head>
<body>

<div class="container review-container">
    <h3 class="text-center mb-4">📝 Submit Internship Review</h3>

    <form method="post" action="<%= request.getContextPath() %>/SubmitReviewServlet">
        <!-- Internship Selection -->
        <div class="mb-3">
            <label class="form-label">Select Internship:</label>
            <select name="internshipId" class="form-select" required>
                <option value="">-- Select --</option>
                <%
                    List<Map<String, String>> acceptedInternships =
                        (List<Map<String, String>>) request.getAttribute("acceptedInternships");

                    if (acceptedInternships != null) {
                        for (Map<String, String> i : acceptedInternships) {
                %>
                    <option value="<%= i.get("id") %>"><%= i.get("title") %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <!-- Star Rating -->
        <div class="mb-3">
            <label class="form-label d-block">Rating:</label>
            <div class="stars text-center">
                <% for (int i = 5; i >= 1; i--) { %>
                    <input type="radio" id="star<%= i %>" name="rating" value="<%= i %>">
                    <label for="star<%= i %>">★</label>
                <% } %>
            </div>
        </div>

        <!-- comment textarea -->
        <div class="mb-3">
            <label class="form-label">Comment:</label>
            <textarea class="form-control" name="comment" rows="4" required placeholder="Share your experience..."></textarea>
        </div>

        <!-- Submit Button -->
        <div class="d-grid">
            <button type="submit" class="btn btn-primary">Submit Review ✅</button>
        </div>
    </form>

    <div class="text-center mt-3">
        <a href="<%= request.getContextPath() %>/views/dashboard_student.jsp" class="btn btn-link">⬅ Back to Dashboard</a>
    </div>
</div>

</body>
</html>
