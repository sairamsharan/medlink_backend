package com.medlink.auth.utils;

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

  public void sendOtpEmail(String email, String otp) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setTo(email);
    helper.setFrom("mail.medlink@gmail.com");
    helper.setSubject("Medlink — Verify Your Account");

    String html = "<html><body style='margin:0;padding:0;font-family:Arial,Helvetica,sans-serif;background-color:#f1f5f9;'>"
        + "<table align='center' width='600' cellpadding='0' cellspacing='0' style='margin:30px auto;background:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 1px 3px rgba(0,0,0,0.1);'>"
        + "<tr><td style='background-color:#2563eb;padding:28px 30px;text-align:center;'>"
        + "<h1 style='margin:0;font-size:26px;color:#ffffff;letter-spacing:-0.5px;'>Med<span style=\"color:#bfdbfe;\">link</span></h1>"
        + "<p style='margin:6px 0 0;font-size:13px;color:#bfdbfe;'>Your Health, Our Priority</p>"
        + "</td></tr>"
        + "<tr><td style='padding:32px 30px 40px;'>"
        + "<h2 style='margin:0 0 16px;font-size:20px;color:#1e293b;'>Verify Your Email</h2>"
        + "<p style='margin:0 0 12px;font-size:14px;color:#475569;line-height:1.6;'>Hello,</p>"
        + "<p style='margin:0 0 24px;font-size:14px;color:#475569;line-height:1.6;'>Use the verification code below to complete your Medlink registration. This code expires in <strong>5 minutes</strong>.</p>"
        + "<div style='text-align:center;margin:0 0 24px;'>"
        + "<div style='display:inline-block;padding:14px 40px;background-color:#f1f5f9;border:2px dashed #2563eb;border-radius:8px;'>"
        + "<span style='font-size:32px;font-weight:700;letter-spacing:6px;color:#1e293b;'>" + otp + "</span>"
        + "</div></div>"
        + "<p style='margin:0 0 6px;font-size:13px;color:#64748b;line-height:1.5;'>If you did not create a Medlink account, you can safely ignore this email.</p>"
        + "<p style='margin:24px 0 0;font-size:14px;color:#475569;'>Regards,<br/><strong>Team Medlink</strong></p>"
        + "</td></tr>"
        + FOOTER
        + "</table></body></html>";

    helper.setText(html, true);
    javaMailSender.send(mimeMessage);
  }
}
