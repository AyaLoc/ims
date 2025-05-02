/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.ApplicationBean;

public class TrackApplicationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"student".equals(session.getAttribute("userRole"))) {
            res.sendRedirect("views/login.jsp");
            return;
        }

        int studentId = (int) session.getAttribute("userId");
        String status = req.getParameter("status");
        String sort = req.getParameter("sort");

        ApplicationBean bean = new ApplicationBean();
        List<Map<String, String>> applications = bean.getApplicationsByStudent(studentId, status, sort);

        req.setAttribute("applications", applications);
        req.setAttribute("selectedStatus", status);
        req.setAttribute("selectedSort", sort);
        req.getRequestDispatcher("views/track_applications.jsp").forward(req, res);
    }
}