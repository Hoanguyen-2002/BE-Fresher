package com.lg.fresher.lgcommerce.service.email;

import com.lg.fresher.lgcommerce.entity.order.Order;
import com.lg.fresher.lgcommerce.entity.order.OrderDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

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
 * 11/20/2024      63200502      add method notify order success
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

    /**
     * @param to
     * @param order
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendNotifyPlaceOrderSuccess(String to, Order order) throws MessagingException {
        List<OrderDetail> orderItems = order.getOrderDetails();
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "Đơn hàng " + order.getOrderId() + " đã được đặt thành công";
        StringBuilder htmlMessage = new StringBuilder();
        htmlMessage.append("<html>")
                .append("<body style=\"font-family: Arial, sans-serif;\">")
                .append("<div style=\"background-color: #f5f5f5; padding: 20px;\">")
                .append("<h2 style=\"color: #333;\">Thông báo Đơn hàng!</h2>")
                .append("<p style=\"font-size: 16px;\">Chào bạn, đơn hàng của bạn đã được đặt thành công và đang trong thời gian xử lý.</p>")
                .append("<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">")
                .append("<h3 style=\"color: #333;\">Chi tiết đơn hàng:</h3>")
                .append("<p style=\"color: #FF0000;\">Mã đơn hàng:</p>")
                .append("<ul style=\"font-size: 16px;\">");

        for (OrderDetail item : orderItems) {
            htmlMessage.append("<li>")
                    .append("<img src=\"")
                    .append(item.getBook().getThumbnail())
                    .append("\" alt=\"")
                    .append(item.getBook().getTitle())
                    .append("\" style=\"width: 50px; height: 50px; margin-right: 10px; vertical-align: middle;\"/>")
                    .append(item.getBook().getTitle())
                    .append(": ")
                    .append(item.getQuantity())
                    .append(" x ")
                    .append(String.format("%.2f", item.getFinalPrice())) // Hiển thị giá mỗi sản phẩm
                    .append(" = ")
                    .append(String.format("%.2f", item.getTotal())) // Hiển thị tổng giá cho sản phẩm
                    .append("</li>");
        }

        htmlMessage.append("</ul>")
                .append("<h4 style=\"color: #333;\">Tổng giá đơn hàng: ")
                .append(String.format("%.2f", order.getTotalAmount())) // Hiển thị tổng giá đơn hàng
                .append("</h4>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlMessage.toString(), true);

        emailSender.send(message);
    }
}
