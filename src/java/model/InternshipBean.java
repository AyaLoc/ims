package model;

import java.sql.*;
import java.util.*;

public class InternshipBean {
    private int id;
    private int employerId;
    private String title;
    private String companyName;
    private String location;
    private String description;
    private Timestamp postedDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getPostedDate() { return postedDate; }
    public void setPostedDate(Timestamp postedDate) { this.postedDate = postedDate; }

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "");
    }

    // create Internship
  public boolean save() {
    try (Connection con = getConnection()) {
        String sql = "INSERT INTO internships (employer_id, title, company_name, location, description) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, employerId);
        ps.setString(2, title);
        ps.setString(3, companyName);
        ps.setString(4, location);
        ps.setString(5, description);

        boolean success = ps.executeUpdate() > 0;

        if (success) {
            // notify all students (basic version)
            notifyAllStudentsAboutNewInternship();
        }

        return success;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
private void notifyAllStudentsAboutNewInternship() {
    try (Connection con = getConnection()) {
         int studentId =0;
        String sql = "SELECT email FROM users WHERE role='student' AND active=1";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        String subject = "🚨 New Internship Opportunity Posted!";
        String body = "Hello Student,\n\nA new internship has just been posted:\n\n" +
                         "Title: " + title + "\n" +
                         "Company: " + companyName + "\n" +
                         "Location: " + location + "\n\n" +
                         "Visit your dashboard to learn more and apply.\n\n" +
                         "Best regards,\nInternship Management System";

        while (rs.next()) {
            String studentEmail = rs.getString("email");
           MailUtil.sendEmail(studentId, studentEmail, subject, body);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // get all internships by employer
    public List<InternshipBean> getAllByEmployer(int empId) {
        List<InternshipBean> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM internships WHERE employer_id=? ORDER BY posted_date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InternshipBean i = new InternshipBean();
                i.setId(rs.getInt("id"));
                i.setEmployerId(rs.getInt("employer_id"));
                i.setTitle(rs.getString("title"));
                i.setCompanyName(rs.getString("company_name"));
                i.setLocation(rs.getString("location"));
                i.setDescription(rs.getString("description"));
                i.setPostedDate(rs.getTimestamp("posted_date"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // delete internship by ID
    public boolean delete(int id) {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM internships WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public InternshipBean getInternshipById(int internshipId) {
    InternshipBean bean = new InternshipBean();
    try (Connection con = getConnection()) {
        String sql = "SELECT * FROM internships WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, internshipId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bean.setId(rs.getInt("id"));
            bean.setTitle(rs.getString("title"));
            bean.setCompanyName(rs.getString("company_name"));
            bean.setLocation(rs.getString("location"));
            bean.setDescription(rs.getString("description"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return bean;
}

public boolean update() {
    try (Connection con = getConnection()) {
        String sql = "UPDATE internships SET title=?, company_name=?, location=?, description=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, companyName);
        ps.setString(3, location);
        ps.setString(4, description);
        ps.setInt(5, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public List<InternshipBean> getPaginatedInternships(int start, int limit) {
        List<InternshipBean> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM internships ORDER BY posted_date DESC LIMIT ?, ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, start);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InternshipBean i = new InternshipBean();
                i.setId(rs.getInt("id"));
                i.setTitle(rs.getString("title"));
                i.setCompanyName(rs.getString("company_name"));
                i.setLocation(rs.getString("location"));
                i.setPostedDate(rs.getTimestamp("posted_date"));
                i.setDescription(rs.getString("description"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalInternshipCount() {
        try (Connection con = getConnection()) {
            String sql = "SELECT COUNT(*) FROM internships";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
      public List<InternshipBean> getInternships(int offset, int limit) {
        List<InternshipBean> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM internships ORDER BY posted_date DESC LIMIT ?, ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InternshipBean internship = new InternshipBean();
                internship.setId(rs.getInt("id"));
                internship.setTitle(rs.getString("title"));
                internship.setCompanyName(rs.getString("company_name"));
                internship.setLocation(rs.getString("location"));
                internship.setDescription(rs.getString("description"));
                internship.setPostedDate(rs.getTimestamp("posted_date"));
                list.add(internship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // get total internship count
    public int getTotalCount() {
        int count = 0;
        try (Connection con = getConnection()) {
            String sql = "SELECT COUNT(*) FROM internships";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public List<Map<String, String>> getAllInternships() {
    List<Map<String, String>> list = new ArrayList<>();
    try (Connection con = getConnection()) {
        String sql = "SELECT i.id, i.title, i.company_name, i.location, i.posted_date FROM internships i";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, String> map = new HashMap<>();
            map.put("id", rs.getString("id"));
            map.put("title", rs.getString("title"));
            map.put("company", rs.getString("company_name"));
            map.put("location", rs.getString("location"));
            map.put("date", rs.getString("posted_date"));
            list.add(map);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
