package com.codeofus.rent_a_park.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void send(String from, String to, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        mimeMessage.setContent(content, "text/html");
        mailSender.send(mimeMessage);

    }
}
