package com.lg.fresher.lgcommerce.service.email;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String to, String text) throws MessagingException;
    void sendNewPassword(String to, String password) throws MessagingException;
}
