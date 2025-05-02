/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.UserBean;


public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password);

        if (user.login()) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("email", user.getEmail());

            
              String contextPath = req.getContextPath();  

            switch (user.getRole()) {
                case "student":
                    res.sendRedirect("views/dashboard_student.jsp");
                    break;
                case "employer":
                    res.sendRedirect("views/dashboard_employer.jsp");
                    break;
                case "admin":
                    res.sendRedirect("views/dashboard_admin.jsp");
                    break;
                default:
                    res.sendRedirect("views/login.jsp");
            }
        } else {
            res.getWriter().println("Login failed. <a href='views/login.jsp'>Try Again</a>");
        }
    }
}

