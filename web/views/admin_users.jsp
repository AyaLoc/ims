<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, model.UserBean" %>
<%
    List<UserBean> users = (List<UserBean>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Manage Users</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        .status-badge {
            font-weight: bold;
            padding: 5px 10px;
            border-radius: 20px;
        }
        .active-badge {
            background-color: #d4edda;
            color: #155724;
        }
        .inactive-badge {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body class="bg-light">
<div class="container py-5">
    <h2 class="text-center mb-4">👥 Manage User Accounts</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (UserBean user : users) {
            %>
                <tr>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getRole().substring(0,1).toUpperCase() + user.getRole().substring(1) %></td>
                    <td>
                        <span class="status-badge <%= user.isActive() ? "active-badge" : "inactive-badge" %>">
                            <%= user.isActive() ? "Active" : "Inactive" %>
                        </span>
                    </td>
                    <td>
                        <form method="post" action="AdminUserManagementServlet" class="d-inline">
                            <input type="hidden" name="userId" value="<%= user.getId() %>" />
                            <% if (user.isActive()) { %>
                                <button type="submit" name="action" value="deactivate" class="btn btn-sm btn-danger">Deactivate</button>
                            <% } else { %>
                                <button type="submit" name="action" value="activate" class="btn btn-sm btn-success">Activate</button>
                            <% } %>
                        </form>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-4">
        <a href="views/dashboard_admin.jsp" class="btn btn-secondary">⬅ Back to Admin Dashboard</a>
    </div>
</div>
</body>
</html>
