package model;

import java.sql.*;
import java.util.*;

public class ReplyBean {
    private int id;
    private int reviewId;
    private int employerId;
    private String comment;
    private java.sql.Date replyDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public java.sql.Date getReplyDate() { return replyDate; }
    public void setReplyDate(java.sql.Date replyDate) { this.replyDate = replyDate; }

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "");
    }

    // save employer's reply
    public boolean save() {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO review_replies (review_id, employer_id, comment, reply_date) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, reviewId);
            ps.setInt(2, employerId);
            ps.setString(3, comment);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // return all approved reviews for this employer 
    public List<Map<String, String>> getAllReviewsForEmployer(int employerId) {
        List<Map<String, String>> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT r.id AS review_id, r.rating, r.comment AS review_comment, r.submission_date, " +
                         "u.username AS student_name, i.title AS internship_title, rr.comment AS reply_comment " +
                         "FROM reviews r " +
                         "JOIN users u ON r.student_id = u.id " +
                         "JOIN internships i ON r.internship_id = i.id " +
                         "LEFT JOIN review_replies rr ON r.id = rr.review_id AND rr.employer_id = ? " +
                         "WHERE i.employer_id = ? AND r.approved = TRUE";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, employerId);
            ps.setInt(2, employerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> review = new HashMap<>();
                review.put("reviewId", rs.getString("review_id"));
                review.put("rating", rs.getString("rating"));
                review.put("comment", rs.getString("review_comment"));
                review.put("submissionDate", rs.getString("submission_date"));
                review.put("studentName", rs.getString("student_name"));
                review.put("internshipTitle", rs.getString("internship_title"));
                review.put("replyComment", rs.getString("reply_comment"));
                list.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getStudentEmailForReview(int reviewId) {
    try (Connection con = getConnection()) {
        String sql = "SELECT u.email FROM reviews r JOIN users u ON r.student_id = u.id WHERE r.id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, reviewId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("email");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    
}
