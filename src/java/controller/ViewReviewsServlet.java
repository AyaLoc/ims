package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.ReplyBean;

public class ViewReviewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"employer".equals(session.getAttribute("userRole"))) {
            res.sendRedirect("views/login.jsp");
            return;
        }

        int employerId = (int) session.getAttribute("userId");

        ReplyBean bean = new ReplyBean();
        List<Map<String, String>> reviews = bean.getAllReviewsForEmployer(employerId);
        req.setAttribute("reviews", reviews);

        req.getRequestDispatcher("views/employer_reviews.jsp").forward(req, res);
    }
}
