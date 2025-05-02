/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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


public class PostInternshipServlet extends HttpServlet {


protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
      
Object sessionUserId = req.getSession().getAttribute("userId");
if (sessionUserId == null) {
    res.getWriter().println("No user session found. Please log in again.");
    return;
}
 int employerId = (int) req.getSession().getAttribute("userId");
        InternshipBean intern = new InternshipBean();
        intern.setEmployerId(employerId);
        intern.setTitle(req.getParameter("title"));
        intern.setCompanyName(req.getParameter("company_name"));
        intern.setLocation(req.getParameter("location"));
        intern.setDescription(req.getParameter("description"));

        if (intern.save()) {
            res.sendRedirect(req.getContextPath() + "/views/manage_internships.jsp");
        } else {
            res.getWriter().println("Error posting internship.");
        }
    }

}
