
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.InternshipBean;


public class DeleteInternshipServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        InternshipBean intern = new InternshipBean();
        if (intern.delete(id)) {
            res.sendRedirect(req.getContextPath() + "/views/manage_internships.jsp");
        } else {
            res.getWriter().println("Error deleting internship.");
        }
    }

}
