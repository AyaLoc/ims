/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.InternshipBean;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ViewInternshipsServlet extends HttpServlet {

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int offset = (page - 1) * recordsPerPage;

        InternshipBean bean = new InternshipBean();
        List<InternshipBean> internships = bean.getInternships(offset, recordsPerPage);
        int total = bean.getTotalCount();
        int totalPages = (int) Math.ceil(total * 1.0 / recordsPerPage);

        request.setAttribute("internships", internships);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        RequestDispatcher dispatcher = request.getRequestDispatcher("views/internships.jsp");
        dispatcher.forward(request, response);
    }
    
}
