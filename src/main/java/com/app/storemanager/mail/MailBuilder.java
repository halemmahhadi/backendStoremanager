package com.app.storemanager.mail;

import com.app.storemanager.user.baseuser.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailBuilder {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String appEmail;

    public void constructAndSendResetVerificationTokenEmail(String contextPath, String token, User user) {
        final String confirmationUrl = contextPath + "/reset_password/?token=" + token + "&id=" + user.getEmail();
        final String message = "To Reset The Password Click On This Link \n";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Reset Password");
        email.setText(message + " \r\n" + confirmationUrl);
        email.setTo(user.getEmail());
        email.setFrom(appEmail);
        emailSender.send(email);
    }

}
