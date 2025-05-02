package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.InternshipBean;
import model.ApplicationBean;

public class MonitorSystemServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("views/login.jsp");
            return;
        }

        InternshipBean internshipBean = new InternshipBean();
        List<Map<String, String>> internships = internshipBean.getAllInternships();

        ApplicationBean applicationBean = new ApplicationBean();
        List<Map<String, String>> applications = applicationBean.getAllApplications();

        // calculate statistics
        int totalInternships = internships.size();
        int totalApplications = applications.size();
        int pendingApplications = 0;

        for (Map<String, String> app : applications) {
            if ("Pending".equalsIgnoreCase(app.get("status"))) {
                pendingApplications++;
            }
        }

        request.setAttribute("internships", internships);
        request.setAttribute("applications", applications);

        // pass statistics
        request.setAttribute("totalInternships", totalInternships);
        request.setAttribute("totalApplications", totalApplications);
        request.setAttribute("pendingApplications", pendingApplications);

        RequestDispatcher dispatcher = request.getRequestDispatcher("views/admin_monitor.jsp");
        dispatcher.forward(request, response);
    }
}
