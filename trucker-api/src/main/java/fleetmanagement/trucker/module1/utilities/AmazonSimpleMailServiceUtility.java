package fleetmanagement.trucker.module1.utilities;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class AmazonSimpleMailServiceUtility {

     private String FROM;
     private String FROMNAME;
     private String TO;
     private String SMTP_USERNAME;
     private String SMTP_PASSWORD;
     private String HOST;
     private int PORT;
     private String SUBJECT;
     private Properties props;

     public AmazonSimpleMailServiceUtility(){
        this.props = new Properties();
        this.FROM = "akhilchowdary87@gmail.com";
        this.FROMNAME = "Akhil";
        this.TO = "akhilk1006@gmail.com";
        this.HOST = "email-smtp.us-west-2.amazonaws.com";
        this.PORT = 587;
        this.SUBJECT = "Vehicle Alert";
        this.SMTP_USERNAME = "AKIAIGQL7LQNRRP4IZ2Q";
        this.SMTP_PASSWORD = "AotHTNBuwWnQV987Dkx0ytPeRcXLwlsUtxIvLLJtOQHj";
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
     }

     @Async
     public void sendEmail(String vehicleIdentity, String message){
         String BODY = String.join(
                         System.getProperty("line.separator"),
                            "<h1>Vehicle Alert</h1>",
                            "<h4>The following alert was triggered on your vehicle</h4>",
                            "<p>Alert: "+ message + "</p>",
                            "<p>Vehicle Identification Number: "+vehicleIdentity+"</p>"
                       );
        Session session = Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(FROM,FROMNAME));
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            msg.setSubject(SUBJECT);
            msg.setContent(BODY,"text/html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Transport transport = null;
        try {
            transport = session.getTransport();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
