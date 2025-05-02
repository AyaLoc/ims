


package model;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.sql.*;

public class MailUtil {

    public static boolean sendEmail(int studentId, String toEmail, String subject, String body) {
        final String fromEmail = "kthejane1@gmail.com"; 
        final String password = "hizl egdo miyr envw";     

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        try {
            Session session = Session.getInstance(props, auth);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);

            // after successful send -> Save in DB
            saveEmail(studentId, subject, body);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void saveEmail(int studentId, String subject, String body) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "")) {
                String sql = "INSERT INTO emails (student_id, subject, body, is_read) VALUES (?, ?, ?, FALSE)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, studentId);
                ps.setString(2, subject);
                ps.setString(3, body);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
