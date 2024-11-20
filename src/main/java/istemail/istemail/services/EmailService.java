package istemail.istemail.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Sends an email using JavaMailSender.
     * 
     * @param fromEmail Sender's email address
     * @param toEmail Recipient's email address
     * @param subject Subject of the email
     * @param body Content of the email (HTML supported)
     * @return EmailResponse custom response object with status and message
     */
    public EmailResponse sendEmail(String fromEmail, String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML content
            
            mailSender.send(message);
            
            return new EmailResponse(true, "Email sent successfully");
            
        } catch (MessagingException e) {
            return new EmailResponse(false, "Failed to send email: " + e.getMessage());
        } catch (Exception e) {
            return new EmailResponse(false, "Unexpected error: " + e.getMessage());
        }
    }
}

class EmailResponse {
    private final boolean status;
    private final String message;
    private final Object data;

    public EmailResponse(boolean status, String message) {
        this(status, message, null);
    }

    public EmailResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}