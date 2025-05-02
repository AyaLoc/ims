
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
import java.util.List;
import model.InternshipBean;


public class BrowseInternshipsServlet extends HttpServlet {

   private static final int RECORDS_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        int start = (page - 1) * RECORDS_PER_PAGE;

        InternshipBean internshipBean = new InternshipBean();
        List<InternshipBean> internships = internshipBean.getPaginatedInternships(start, RECORDS_PER_PAGE);
        int total = internshipBean.getTotalInternshipCount();
        int totalPages = (int) Math.ceil(total * 1.0 / RECORDS_PER_PAGE);

        req.setAttribute("internships", internships);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        RequestDispatcher rd = req.getRequestDispatcher("views/browse_internships.jsp");
        rd.forward(req, res);
    }
}
