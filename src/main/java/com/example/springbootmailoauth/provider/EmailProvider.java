package com.example.springbootmailoauth.provider;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProvider {

    @Value("${spring.mail.sendFrom}")
    private String sendFrom;

    private final JavaMailSender javaMailSender;

    private final String SUBJECT = "[MailOAuth 이메일 인증 서비스 테스트.] 인증메일입니다. ";

    public boolean sendCertificationMail(String email, String certificationNumber) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setFrom(sendFrom);
            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;

    }

    private String getCertificationMessage(String certificationNumber) {

        String certificationMessage = "";
        certificationMessage += "<h1 style = 'text-align: center;'>[MailOAuth 이메일 인증 서비스 테스트.] 인증메일입니다.</h1>";
        certificationMessage += "<h3 style = 'text-align: center;'>인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>" + certificationNumber + "</strong></h3>";
        return certificationMessage;
    }
}
