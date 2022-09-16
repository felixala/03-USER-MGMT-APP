package com.felixlaura.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {

    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body){
        boolean isMailSent = false;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            mailSender.send(mimeMessage);
            isMailSent = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return isMailSent;
    }

}
