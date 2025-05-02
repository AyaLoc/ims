package model;

import java.sql.*;
import java.util.*;

public class ReviewBean {
    private int id;
    private int studentId;
    private int internshipId;
    private int rating;
    private String comment;
    private java.sql.Date submissionDate;
    private boolean approved;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getInternshipId() { return internshipId; }
    public void setInternshipId(int internshipId) { this.internshipId = internshipId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public java.sql.Date getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(java.sql.Date submissionDate) { this.submissionDate = submissionDate; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    // DB Connection
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "");
    }

    // save a new review
    public boolean save() {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO reviews (student_id, internship_id, rating, comment, submission_date, approved) " +
                         "VALUES (?, ?, ?, ?, CURDATE(), FALSE)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, internshipId);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // approve review
    public boolean approveReview(int reviewId) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE reviews SET approved = TRUE WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, reviewId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // delete review
    public boolean deleteReview(int reviewId) {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM reviews WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, reviewId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // get all reviews for admin moderation
    public List<Map<String, String>> getAllReviewsForModeration() {
        List<Map<String, String>> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT r.id, r.comment, r.rating, r.submission_date, r.approved, " +
                         "u.username AS student_name, i.title AS internship_title " +
                         "FROM reviews r " +
                         "JOIN users u ON r.student_id = u.id " +
                         "JOIN internships i ON r.internship_id = i.id";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("id", rs.getString("id"));
                map.put("studentName", rs.getString("student_name"));
                map.put("internshipTitle", rs.getString("internship_title"));
                map.put("comment", rs.getString("comment"));
                map.put("rating", rs.getString("rating"));
                map.put("date", rs.getString("submission_date"));
                map.put("approved", rs.getBoolean("approved") ? "true" : "false"); // ✅ proper boolean string
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
