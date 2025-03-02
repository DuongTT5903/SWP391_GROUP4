package controller;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author HP
 */
public class resetService {
    private final int LIMIT_MINUS = 10;
    static final String from = "quankhong111222@gmail.com";
    static final String password = "zqva ikvq oxuc hlef";
    
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
        
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        
        Session session = Session.getInstance(props, auth);
        
        MimeMessage msg = new MimeMessage(session);
        
        try {
            msg.addHeader("Content-type", "text/html; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject("Reset Password", "UTF-8");
            String content = "<h1>Hello " + name + "</h1>" + "<p>Click the link to reset your password "
                    + "<a href=\"" + link + "\">Click here</a></p>";
            msg.setContent(content, "text/html; charset=UTF-8");
            Transport.send(msg);
            System.out.println("Send successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Send error");
            e.printStackTrace(); // Use e.printStackTrace() to print the stack trace for debugging
            return false;
        }
    }

    /**
     * Main method to test token generation and expiration.
     */
    public static void main(String[] args) {
        resetService service = new resetService();

        // 📌 Generate token and expiry time
        String token = service.generateToken();
        LocalDateTime expireTime = service.expireDateTime();

        // 📌 Print debug info
        System.out.println("========== 🛠 DEBUG INFO ==========");
        System.out.println("🔑 Generated Token: " + token);
        System.out.println("🕒 Expiry Time: " + expireTime);
        System.out.println("🕒 Current Time: " + LocalDateTime.now());
        System.out.println("⏳ Is Expired? " + service.isExpireTime(expireTime));
        System.out.println("===================================");

        // 📌 Wait 11 minutes to check expiration (optional)
        try {
            Thread.sleep(11 * 60 * 1000); // Wait 11 minutes
            System.out.println("\n🔄 Rechecking after 11 minutes...");
            System.out.println("🕒 Current Time: " + LocalDateTime.now());
            System.out.println("⏳ Is Expired? " + service.isExpireTime(expireTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
