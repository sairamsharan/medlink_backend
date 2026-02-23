package com.medlink.appointment.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String FOOTER = ""
            + "<tr>"
            + "<td style='padding:24px 30px;background-color:#f8fafc;border-top:1px solid #e2e8f0;'>"
            + "<p style='margin:0 0 8px;font-size:12px;color:#64748b;text-align:center;'>This is an automated email from Medlink. Please do not reply directly.</p>"
            + "<p style='margin:0 0 8px;font-size:12px;color:#64748b;text-align:center;'>&copy; 2026 Medlink &mdash; Built by students at IIIT Lucknow</p>"
            + "<p style='margin:0;font-size:11px;color:#94a3b8;text-align:center;'>Beta release &middot; For issues contact mail.medlink@gmail.com</p>"
            + "</td>"
            + "</tr>";

    public void sendAppointmentConfirmationEmail(String email, String fullName, String appointmentDate,
            String appointmentTime) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setFrom("mail.medlink@gmail.com");
        helper.setSubject("Medlink — Appointment Confirmed");

        String html = "<html><body style='margin:0;padding:0;font-family:Arial,Helvetica,sans-serif;background-color:#f1f5f9;'>"
                + "<table align='center' width='600' cellpadding='0' cellspacing='0' style='margin:30px auto;background:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 1px 3px rgba(0,0,0,0.1);'>"
                + "<tr><td style='background-color:#16a34a;padding:28px 30px;text-align:center;'>"
                + "<h1 style='margin:0;font-size:26px;color:#ffffff;letter-spacing:-0.5px;'>Med<span style=\"color:#bbf7d0;\">link</span></h1>"
                + "<p style='margin:6px 0 0;font-size:13px;color:#bbf7d0;'>Appointment Confirmed</p>"
                + "</td></tr>"
                + "<tr><td style='padding:32px 30px 40px;'>"
                + "<h2 style='margin:0 0 16px;font-size:20px;color:#1e293b;'>Your Appointment Details</h2>"
                + "<p style='margin:0 0 20px;font-size:14px;color:#475569;line-height:1.6;'>Hello " + fullName + ",</p>"
                + "<p style='margin:0 0 20px;font-size:14px;color:#475569;line-height:1.6;'>Your appointment has been successfully scheduled. Here are the details:</p>"
                + "<table width='100%' cellpadding='0' cellspacing='0' style='margin:0 0 24px;border:1px solid #e2e8f0;border-radius:6px;overflow:hidden;'>"
                + "<tr><td style='padding:12px 16px;background-color:#f8fafc;font-size:13px;color:#64748b;width:120px;border-bottom:1px solid #e2e8f0;'>Date</td>"
                + "<td style='padding:12px 16px;font-size:14px;color:#1e293b;font-weight:600;border-bottom:1px solid #e2e8f0;'>"
                + appointmentDate + "</td></tr>"
                + "<tr><td style='padding:12px 16px;background-color:#f8fafc;font-size:13px;color:#64748b;width:120px;'>Time</td>"
                + "<td style='padding:12px 16px;font-size:14px;color:#1e293b;font-weight:600;'>" + appointmentTime
                + "</td></tr>"
                + "</table>"
                + "<p style='margin:0 0 6px;font-size:13px;color:#64748b;line-height:1.5;'>Please arrive 15 minutes early and bring a valid ID along with any relevant medical records.</p>"
                + "<p style='margin:16px 0 0;font-size:13px;color:#64748b;line-height:1.5;'>If you did not book this appointment, please contact us immediately.</p>"
                + "<p style='margin:24px 0 0;font-size:14px;color:#475569;'>Regards,<br/><strong>Team Medlink</strong></p>"
                + "</td></tr>"
                + FOOTER
                + "</table></body></html>";

        helper.setText(html, true);
        javaMailSender.send(mimeMessage);
    }
}
