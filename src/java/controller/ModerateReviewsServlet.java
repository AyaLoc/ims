package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.ReviewBean;

public class ModerateReviewsServlet extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ReviewBean bean = new ReviewBean();
        List<Map<String, String>> reviews = bean.getAllReviewsForModeration();

        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("views/review_moderation.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String reviewIdStr = request.getParameter("reviewId");

        if (reviewIdStr == null || reviewIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ModerateReviewsServlet");
            return;
        }

        int reviewId = Integer.parseInt(reviewIdStr);
        ReviewBean bean = new ReviewBean();
        boolean result = false;

        if ("approve".equals(action)) {
            result = bean.approveReview(reviewId);
        } else if ("delete".equals(action)) {
            result = bean.deleteReview(reviewId);
        }

        response.sendRedirect(request.getContextPath() + "/ModerateReviewsServlet");
    }
}
