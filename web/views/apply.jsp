<%-- 
    Document   : apply
    Created on : Apr 18, 2025, 4:23:27 PM
    Author     : user
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.*, jakarta.servlet.*" %>

<%
    String internshipIdStr = request.getParameter("internshipId");

    if (internshipIdStr == null || internshipIdStr.isEmpty()) {
%>
    <p style="color:red;">
        ❌ internshipId is missing. 
        <a href="<%= request.getContextPath() %>/BrowseInternshipsServlet">Go back</a>
    </p>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Apply for Internship</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">

</head>
<body>
    <h2>Internship Application</h2>

<form action="<%= request.getContextPath() %>/ApplyInternshipServlet?internshipId=<%= internshipIdStr %>" method="post" enctype="multipart/form-data">

        <label>CV (PDF only):</label><br>
        <input type="file" name="cv" accept="application/pdf" required /><br><br>

        <label>Transcript (PDF only):</label><br>
        <input type="file" name="transcript" accept="application/pdf" required /><br><br>

        <button type="submit">Submit Application</button>
    </form>

    <p><a href="<%= request.getContextPath() %>/BrowseInternshipsServlet">Back to Listings</a></p>
</body>
</html>
