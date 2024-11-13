package com.lg.fresher.lgcommerce.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 *
 -------------------------------------------------------------------------
 * LG CNS Ecommerce
 *------------------------------------------------------------------------
 * @ Class Name : EmailConfig
 * @ Description : lg_ecommerce_be EmailConfig
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/6/2024
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/6/2024       63200502      first creation */
@Configuration
public class EmailConfig {
    @Value("${spring.mail.username}")
    private String emailUsername;
    @Value("${spring.mail.password}")
    private String emailPassword;
    @Value("${spring.mail.host}")
    private String emailServerHost;
    @Value("${spring.mail.port}")
    private int emailServerPort;

    /**
     *
     * @ Description : lg_ecommerce_be EmailConfig Member Field javaMailSender
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/6/2024           63200502    first creation
     *<pre>
     * @return  JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailServerHost);
        mailSender.setPort(emailServerPort);
        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
