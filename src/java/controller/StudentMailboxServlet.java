package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.StudentMailBean;

public class StudentMailboxServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int studentId = (int) request.getSession().getAttribute("userId");

        StudentMailBean mailBean = new StudentMailBean();
        List<Map<String, String>> emails = mailBean.getEmailsForStudent(studentId);

        // mark as read now
        mailBean.markAllAsRead(studentId);

        request.setAttribute("emails", emails);
        request.getRequestDispatcher("views/student_emails.jsp").forward(request, response);
    }
}
