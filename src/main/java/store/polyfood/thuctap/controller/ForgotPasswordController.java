package store.polyfood.thuctap.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.models.responobject.Utility;
import store.polyfood.thuctap.services.ForgotPasswordService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping(value = "")

public class ForgotPasswordController {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public ResponseEntity<String> forgotPassword(HttpServletRequest request, @RequestParam String email) {
        String token = generateRandomString(30);

        try {
            forgotPasswordService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            return ResponseEntity.ok("We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("doducminh232002@gmail.com", "Dominh");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @RequestMapping(value ="/reset_password", method = RequestMethod.POST)
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Account account = forgotPasswordService.getByResetPasswordToken(token);
        if(account == null) {
            return ResponseEntity.status(404).body("Token invalid.");
        } else {
            int expiry = LocalDateTime.now().compareTo(account.getResetPasswordTokenExpiry());
            if(expiry > 0) {
                return ResponseEntity.status(400).body("Token has expired.");
            } else {
                forgotPasswordService.updatePassword(account,newPassword);
                return ResponseEntity.status(200).body("Password has been updated");
            }
        }
    }
}
