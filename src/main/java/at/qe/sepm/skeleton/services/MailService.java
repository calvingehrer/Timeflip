package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * source: https://www.baeldung.com/spring-email
 */

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * sends mail to
     * @param toAddress
     * @param subject
     * @param text
     */
    public void sendMail(String toAddress, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }

    public void sendEmailTo(User user, String subject, String text) {
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            sendMail(user.getEmail(), subject, text);
        }
    }
}
