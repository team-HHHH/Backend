package com.hhhh.dodream.global.common.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.email.adress}")
    private String senderEmail;

    private MimeMessage createMail(String mail, Integer authCode) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증 번호");
            String body = "";
            body += "<h3>" + "회원가입을 위한 인증 번호입니다." + "</h3>";
            body += "<h1>" + authCode + "</h1>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    @Async
    public void sendAuthCodeOnMail(String mail, Integer authCode) {
        MimeMessage message = createMail(mail, authCode);
        javaMailSender.send(message);
    }
}
