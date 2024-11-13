package com.lg.fresher.lgcommerce.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : EmailServiceImpl
 * @ Description : lg_ecommerce_be EmailServiceImpl
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation
 * 11/6/2024       63200502      add method send reset pass
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    public JavaMailSender emailSender;

    /**
     * @param to
     * @param otp
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendVerificationEmail(String to, String otp) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + otp;
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlMessage, true);

        emailSender.send(message);
    }

    /**
     * @param to
     * @param password
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendNewPassword(String to, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "Account Verification";
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Hola!</h2>"
                + "<p style=\"font-size: 16px;\">Here is your new paasword, please change it!</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Your new password:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + password + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlMessage, true);

        emailSender.send(message);
    }
}
