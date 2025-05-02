package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.ReplyBean;
import model.ReviewBean;
import model.MailUtil;

public class ReviewReplyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int employerId = (int) request.getSession().getAttribute("userId");
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        String replyComment = request.getParameter("reply");
         int studentId = (int) request.getSession().getAttribute("userId");
         
        ReplyBean bean = new ReplyBean();
        bean.setEmployerId(employerId);
        bean.setReviewId(reviewId);
        bean.setComment(replyComment);

        boolean saved = bean.save();

        // send email to the student
        String studentEmail = bean.getStudentEmailForReview(reviewId);
        if (studentEmail != null && !studentEmail.isEmpty()) {
            String subject = "New Reply to Your Internship Review";
            String body = "Hello,\n\nAn employer has replied to your review:\n\n" +
                             "Reply: " + replyComment + "\n\nBest regards,\nIMS System";
           MailUtil.sendEmail(studentId, studentEmail, subject, body);

        }
        if (saved) {
            response.sendRedirect("ViewReviewsServlet");
        } else {
            response.getWriter().println("❌ Failed to submit reply.");
        }
    }
}
