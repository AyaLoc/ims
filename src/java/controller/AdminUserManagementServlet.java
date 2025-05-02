package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.UserBean;

public class AdminUserManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserBean userBean = new UserBean();
        List<UserBean> allUsers = userBean.getAllUsers();
        request.setAttribute("users", allUsers);

        RequestDispatcher rd = request.getRequestDispatcher("views/admin_users.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String action = request.getParameter("action");

        UserBean userBean = new UserBean();
        if ("activate".equals(action)) {
            userBean.setActiveStatus(userId, true);
        } else if ("deactivate".equals(action)) {
            userBean.setActiveStatus(userId, false);
        }

        response.sendRedirect("AdminUserManagementServlet");
    }
}
