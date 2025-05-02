package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.util.List;
import java.util.Map;
import model.ApplicationBean;
import model.MailUtil;

public class ManageApplicationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"employer".equals(session.getAttribute("userRole"))) {
            res.sendRedirect("views/login.jsp");
            return;
        }

        int employerId = (int) session.getAttribute("userId");
        ApplicationBean appBean = new ApplicationBean();
        List<Map<String, String>> applications = appBean.getApplicationsByEmployer(employerId);

        req.setAttribute("applications", applications);
        RequestDispatcher rd = req.getRequestDispatcher("views/manage_applications.jsp");
        rd.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        int appId = Integer.parseInt(req.getParameter("appId"));
         int studentId = (int) req.getSession().getAttribute("userId");
        ApplicationBean appBean = new ApplicationBean();
        boolean success = appBean.updateStatus(appId, action);

        if (success) {
            // get student email
            String studentEmail = appBean.getStudentEmail(appId);
            if (studentEmail != null && !studentEmail.isEmpty()) {
                // write email
                String subject = "Your Internship Application Status Has Been Updated";
                String body = "Dear Student,\n\n"
                               + "Your application has been updated to the following status: " + action + ".\n"
                               + "Please log in to your dashboard to see more details.\n\n"
                               + "Best regards,\nInternship Management System";

                // gend the email
               MailUtil.sendEmail(studentId, studentEmail, subject, body);
            }
        }

        res.sendRedirect("ManageApplicationsServlet");
    }
}
