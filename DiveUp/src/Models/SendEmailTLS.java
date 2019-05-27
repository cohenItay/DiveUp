package Models;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SendEmailTLS implements Runnable{

	private String toAddress;
	private String title;
	private String content;
	
	public SendEmailTLS(String toAddress, String title, String content)
	{
		this.toAddress = toAddress;
		this.title= title;
		this.content = content;
		Thread t = new Thread(this);
        t.start();
	}
        
	@Override
	public void run() {
		// TODO Auto-generated method stub
		final String username = "maorlolz1@gmail.com";
        final String password = "ocjpgchmxyvqfwkk";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maorlolz1@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("maorlolz1@gmail.com, "+toAddress)
            );
            message.setSubject(title);
            message.setText(content);
            MimeMultipart multipart = new MimeMultipart("related");
            
          
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H3 align="+"\"right\">"+content+"</H3><img src=\"cid:image\">";
            try {
				messageBodyPart.setContent(new String(htmlText.getBytes("UTF8"),"ISO-8859-1"), "text/html");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // add it
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:\\Users\\H357229\\git\\DiveUp\\DiveUp\\src\\images\\snorkel.PNG");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);
            
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

	}

