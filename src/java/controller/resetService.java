package controller;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Service for resetting passwords via email.
 */
public class resetService {  // Tên class nên dùng PascalCase
    private static final int LIMIT_MINUS = 10;
    private static final String FROM_EMAIL = "quankhong111222@gmail.com";
    private static final String PASSWORD = "zqva ikvq oxuc hlef";

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime expireDateTime() {
        return LocalDateTime.now().plusMinutes(LIMIT_MINUS);
    }

    public boolean isExpireTime(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }

    public boolean sendEmail(String to, String link, String name) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new EmailAuthenticator());

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/html; charset=UTF-8");
            msg.setFrom(new InternetAddress(FROM_EMAIL));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject("Reset Password", "UTF-8");

            String content = "<h1>Hello " + name + "</h1>" +
                    "<p>Click the link to reset your password " +
                    "<a href=\"" + link + "\">Click here</a></p>";

            msg.setContent(content, "text/html; charset=UTF-8");

            Transport.send(msg);
            System.out.println("Send successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Send error");
            e.printStackTrace();
            return false;
        }
    }
      public String sendVerificationEmail(String to, String name) {
        String otp = generateOTP(); // Tạo mã OTP
        String content = "<h1>Xin chào " + name + "</h1>" +
                "<p>Đây là mã xác thực email của bạn:</p>" +
                "<h2 style='color:blue;'>" + otp + "</h2>" +
                "<p>Vui lòng nhập mã này để xác nhận email.</p>";
        boolean sent = sendEmail(to, "Email Verification Code", content);
        return sent ? otp : null;
    }

    /**
     * Tạo mã OTP gồm 6 chữ số.
     */
    private String generateOTP() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000); // Tạo số ngẫu nhiên 6 chữ số
        return String.valueOf(otp);
    }

    // Tạo class riêng cho Authenticator
    private static class EmailAuthenticator extends Authenticator {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
        }
    }
}
