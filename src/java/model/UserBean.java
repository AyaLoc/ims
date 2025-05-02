package model;

import java.sql.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class UserBean {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean active;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    private static final String EMPLOYER_USERNAME = "emp";
    private static final String EMPLOYER_PASSWORD = "emp123";

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    // password hashing 
    private String hashPassword(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(plainPassword.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return plainPassword;
        }
    }

   
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/2230652", "root", "");
    }

    // login method
    public boolean login() {
        System.out.println("Login Attempt: " + username + " | Password: " + password);

        // hardcoded Admin check 
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            this.role = "admin";
            this.id =  66;
            return true;
        }

        // hardcoded Employer check
        if (username.equals(EMPLOYER_USERNAME) && password.equals(EMPLOYER_PASSWORD)) {
            this.role = "employer";
            this.id = 4;
            return true;
        }

        // student login from DB 
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=? AND active=1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashPassword(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
                role = rs.getString("role");
                email = rs.getString("email");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // registration (students only)
    public boolean register() {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, 'student')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, hashPassword(password));
            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException dup) {
            System.out.println("Username or Email already exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // fetch all users 
public List<UserBean> getAllUsers() {
    List<UserBean> list = new ArrayList<>();
    try (Connection con = getConnection()) {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            UserBean user = new UserBean();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setActive(rs.getBoolean("active"));
            list.add(user);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

public boolean setActiveStatus(int userId, boolean isActive) {
    try (Connection con = getConnection()) {
        String sql = "UPDATE users SET active = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setBoolean(1, isActive);
        ps.setInt(2, userId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}
