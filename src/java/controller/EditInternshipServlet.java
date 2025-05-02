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
import jakarta.servlet.annotation.*;
import model.InternshipBean;


@WebServlet("/EditInternshipServlet")
public class EditInternshipServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int internshipId = Integer.parseInt(request.getParameter("id"));
        InternshipBean internship = new InternshipBean();
        InternshipBean loaded = internship.getInternshipById(internshipId);

        request.setAttribute("internship", loaded);
        request.getRequestDispatcher("views/edit_internship.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String company_name = request.getParameter("company_name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");

        InternshipBean internship = new InternshipBean();
        internship.setId(id);
        internship.setTitle(title);
        internship.setCompanyName(company_name);
        internship.setLocation(location);
        internship.setDescription(description);

        if (internship.update()) {
         response.sendRedirect(request.getContextPath() + "/views/manage_internships.jsp");
        } else {
            response.getWriter().println("Failed to update internship.");
        }
    }
}