package controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ReviewBean;

public class SubmitReviewServlet extends HttpServlet {

    // show review form
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    int studentId = (int) request.getSession().getAttribute("userId");

    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "")) {
        String sql = "SELECT i.id, i.title FROM internships i " +
                     "JOIN applications a ON i.id = a.internship_id " +
                     "WHERE a.student_id = ? AND a.status = 'Accepted'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();

        List<Map<String, String>> internships = new ArrayList<>();
        while (rs.next()) {
            Map<String, String> item = new HashMap<>();
            item.put("id", rs.getString("id"));
            item.put("title", rs.getString("title"));
            internships.add(item);
        }

        request.setAttribute("acceptedInternships", internships);
    } catch (Exception e) {
        e.printStackTrace();
    }

    request.getRequestDispatcher("views/submit_review.jsp").forward(request, response);
}


    // handle form submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int studentId = (int) request.getSession().getAttribute("userId");
        int internshipId = Integer.parseInt(request.getParameter("internshipId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        ReviewBean review = new ReviewBean();
        review.setStudentId(studentId);
        review.setInternshipId(internshipId);
        review.setRating(rating);
        review.setComment(comment);

        if (review.save()) {
            response.getWriter().println("✅ Review submitted successfully!");
        } else {
            response.getWriter().println("❌ Failed to submit review.");
        }
    }
}
