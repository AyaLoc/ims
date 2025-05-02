package model;

import java.sql.*;
import java.util.*;

public class ApplicationBean {
    private int id;
    private int internshipId;
    private int studentId;
    private String cvPath;
    private String transcriptPath;
    private String status;
    private Timestamp appliedAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInternshipId() { return internshipId; }
    public void setInternshipId(int internshipId) { this.internshipId = internshipId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getCvPath() { return cvPath; }
    public void setCvPath(String cvPath) { this.cvPath = cvPath; }

    public String getTranscriptPath() { return transcriptPath; }
    public void setTranscriptPath(String transcriptPath) { this.transcriptPath = transcriptPath; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Timestamp appliedAt) { this.appliedAt = appliedAt; }

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "");
    }

    // save application to database
    public boolean save() {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO applications (student_id, internship_id, cv_path, transcript_path, status, applied_date) " +
                         "VALUES (?, ?, ?, ?, ?, NOW())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, internshipId);
            ps.setString(3, cvPath);
            ps.setString(4, transcriptPath);
            ps.setString(5, status);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, String>> getApplicationsByEmployer(int employerId) {
        List<Map<String, String>> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT a.id, a.status, a.cv_path, a.transcript_path, a.applied_date, " +
                         "u.username AS student_name, i.title AS internship_title " +
                         "FROM applications a " +
                         "JOIN users u ON a.student_id = u.id " +
                         "JOIN internships i ON a.internship_id = i.id " +
                         "WHERE i.employer_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> app = new HashMap<>();
                app.put("id", rs.getString("id"));
                app.put("status", rs.getString("status"));
                app.put("cvPath", rs.getString("cv_path"));
                app.put("transcriptPath", rs.getString("transcript_path"));
                app.put("applied_date", rs.getString("applied_date"));
                app.put("studentName", rs.getString("student_name"));
                app.put("internshipTitle", rs.getString("internship_title"));
                list.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int applicationId, String newStatus) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE applications SET status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, applicationId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // get applications by student
public List<Map<String, String>> getApplicationsByStudent(int studentId, String filterStatus, String sortOrder) {
    List<Map<String, String>> list = new ArrayList<>();
    try (Connection con = getConnection()) {
        StringBuilder sql = new StringBuilder(
            "SELECT a.id, a.status, a.cv_path, a.transcript_path, a.applied_date, " +
            "i.title AS internship_title, i.company_name " +
            "FROM applications a " +
            "JOIN internships i ON a.internship_id = i.id " +
            "WHERE a.student_id = ?"
        );

        if (filterStatus != null && !filterStatus.equalsIgnoreCase("all")) {
            sql.append(" AND a.status = ?");
        }

        if (sortOrder != null && (sortOrder.equalsIgnoreCase("asc") || sortOrder.equalsIgnoreCase("desc"))) {
            sql.append(" ORDER BY a.applied_date ").append(sortOrder);
        } else {
            sql.append(" ORDER BY a.applied_date DESC");
        }

        PreparedStatement ps = con.prepareStatement(sql.toString());

        ps.setInt(1, studentId);
        if (filterStatus != null && !filterStatus.equalsIgnoreCase("all")) {
            ps.setString(2, filterStatus);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, String> app = new HashMap<>();
            app.put("id", rs.getString("id"));
            app.put("status", rs.getString("status"));
            app.put("cvPath", rs.getString("cv_path"));
            app.put("transcriptPath", rs.getString("transcript_path"));
            app.put("appliedAt", rs.getString("applied_date"));
            app.put("internshipTitle", rs.getString("internship_title"));
            app.put("companyName", rs.getString("company_name"));
            list.add(app);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
public String getStudentEmail(int appId) {
    try (Connection con = getConnection()) {
        String sql = "SELECT u.email FROM applications a JOIN users u ON a.student_id = u.id WHERE a.id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, appId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("email");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public List<Map<String, String>> getAllApplications() {
    List<Map<String, String>> list = new ArrayList<>();
    try (Connection con = getConnection()) {
        String sql = "SELECT a.id, u.username AS student, i.title AS internship, a.status, a.applied_date " +
                     "FROM applications a " +
                     "JOIN users u ON a.student_id = u.id " +
                     "JOIN internships i ON a.internship_id = i.id";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, String> map = new HashMap<>();
            map.put("id", rs.getString("id"));
            map.put("student", rs.getString("student"));
            map.put("internship", rs.getString("internship"));
            map.put("status", rs.getString("status"));
            map.put("date", rs.getString("applied_date"));
            list.add(map);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


}
