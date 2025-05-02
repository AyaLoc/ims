package model;

import java.sql.*;
import java.util.*;

public class StudentMailBean {
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "");
    }

    public void saveEmail(int studentId, String subject, String body) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO emails (student_id, subject, body, is_read) VALUES (?, ?, ?, FALSE)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setString(2, subject);
            ps.setString(3, body);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getEmailsForStudent(int studentId) {
        List<Map<String, String>> emails = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM emails WHERE student_id = ? ORDER BY sent_date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> email = new HashMap<>();
                email.put("subject", rs.getString("subject"));
                email.put("body", rs.getString("body"));
                email.put("sentDate", rs.getString("sent_date"));
                email.put("isRead", rs.getString("is_read"));
                emails.add(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }

    public int getUnreadEmailCount(int studentId) {
        try (Connection con = getConnection()) {
            String sql = "SELECT COUNT(*) AS unreadCount FROM emails WHERE student_id = ? AND is_read = FALSE";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("unreadCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void markAllAsRead(int studentId) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE emails SET is_read = TRUE WHERE student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
