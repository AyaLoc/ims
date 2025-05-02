<%-- 
    Document   : internships
    Created on : Apr 16, 2025, 11:57:09 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Internship Listings</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
<script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">

</head>
<body>
    <h2>Available Internships</h2>

    <c:forEach var="internship" items="${internships}">
        <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
            <h3>${internship.title}</h3>
            <p><strong>Company:</strong> ${internship.companyName}</p>
            <p><strong>Location:</strong> ${internship.location}</p>
            <p><strong>Posted:</strong> ${internship.postedDate}</p>
            <p>${internship.description}</p>
            <form action="apply" method="post">
                <input type="hidden" name="internshipId" value="${internship.id}" />
                <button type="submit">Apply</button>
            </form>
        </div>
    </c:forEach>

    <!-- pagination controls -->
    <div>
        <c:if test="${currentPage > 1}">
            <a href="internships?page=${currentPage - 1}">Previous</a>
        </c:if>

        Page ${currentPage} of ${totalPages}

        <c:if test="${currentPage < totalPages}">
            <a href="internships?page=${currentPage + 1}">Next</a>
        </c:if>
    </div>
</body>
</html>