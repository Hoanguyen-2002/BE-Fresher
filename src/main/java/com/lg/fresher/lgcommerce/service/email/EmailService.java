package com.lg.fresher.lgcommerce.service.email;

import com.lg.fresher.lgcommerce.entity.order.Order;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String to, String text) throws MessagingException;
    void sendNewPassword(String to, String password) throws MessagingException;
    void sendNotifyPlaceOrderSuccess(String to, Order order) throws MessagingException;
}
