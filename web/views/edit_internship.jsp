<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Internship</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        body {
            background-color: #f5f8fa;
        }
        .container {
            max-width: 700px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>✏️ Edit Internship</h2>

    <form action="${pageContext.request.contextPath}/EditInternshipServlet" method="post">
        <input type="hidden" name="id" value="${internship.id}" />

        <div class="mb-3">
            <label class="form-label">Title</label>
            <input type="text" name="title" class="form-control" value="${internship.title}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Company</label>
            <input type="text" name="company_name" class="form-control" value="${internship.companyName}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Location</label>
            <input type="text" name="location" class="form-control" value="${internship.location}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Description</label>
            <textarea name="description" class="form-control" rows="5" required>${internship.description}</textarea>
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-success">💾 Update Internship</button>
        </div>
    </form>

    <div class="mt-3 text-center">
        <a href="<%= request.getContextPath() %>/views/dashboard_employer.jsp" class="btn btn-outline-secondary">⬅ Back to Dashboard</a>
    </div>
</div>

</body>
</html>
