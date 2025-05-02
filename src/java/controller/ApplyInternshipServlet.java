package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.*;
import model.ApplicationBean;
import model.MailUtil;

@MultipartConfig
public class ApplyInternshipServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String internshipIdStr = request.getParameter("internshipId");

        if (internshipIdStr == null || internshipIdStr.isEmpty()) {
            response.getWriter().println("❌ internshipId is missing from the form.");
            return;
        }

        int internshipId = Integer.parseInt(internshipIdStr);
        int studentId = (int) request.getSession().getAttribute("userId");

        // handle file uploads
        Part cvPart = request.getPart("cv");
        Part transcriptPart = request.getPart("transcript");

        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String cvFileName = System.currentTimeMillis() + "_" + cvPart.getSubmittedFileName();
        cvPart.write(uploadPath + File.separator + cvFileName);

        String transcriptFileName = System.currentTimeMillis() + "_" + transcriptPart.getSubmittedFileName();
        transcriptPart.write(uploadPath + File.separator + transcriptFileName);

        // save to db
        ApplicationBean app = new ApplicationBean();
        app.setStudentId(studentId);
        app.setInternshipId(internshipId);
        app.setCvPath(cvFileName);
        app.setTranscriptPath(transcriptFileName);
        app.setStatus("Pending");

     if (app.save()) {
    // send email confirmation
    String studentEmail = (String) request.getSession().getAttribute("email");
    if (studentEmail != null && !studentEmail.isEmpty()) {
        String subject = "Internship Application Submitted";
        String body = "Dear Student,\n\nYou have successfully applied for the internship (ID: " + internshipId + ").\n\nStatus: Pending\n\nBest of luck!\nIMS System";
       MailUtil.sendEmail(studentId, studentEmail, subject, body);

    }

            response.getWriter().println("✅ Application submitted successfully!");
            response.setHeader("Refresh", "2; URL=BrowseInternshipsServlet");

        } else {
            response.getWriter().println("❌ Failed to submit application.");
        }
    }
}
