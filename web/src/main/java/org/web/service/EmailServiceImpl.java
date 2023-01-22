package org.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/*@Service
public class EmailServiceImpl implements EmailService  {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toAddress, String fromAddress, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setText(messageBody);
        message.setSubject(subject);
        javaMailSender.send(message);
    }
}*/
