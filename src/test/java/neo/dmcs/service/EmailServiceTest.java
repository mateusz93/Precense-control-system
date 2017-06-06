package neo.dmcs.service;

import org.junit.Ignore;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */
public class EmailServiceTest {

    @Ignore
    @Test
    public void sendEmail() throws Exception {

        String recipient = "wskmateusz@gmail.com";
        String subject = "Nowa testowa wiadomosc";
        String content = "Co jest z tobą ;)";

        String from = "dmcs.p.lodz.pl@gmail.com";
        String username = "dmcs.p.lodz.pl";
        String password = "dmcs1234";
        String port = "587";
        String host = "smtp.gmail.com";

        Properties props = getProperties(host, port);
        Session session = getSession(username, password, props);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            assertThat(true, is(true));
        } catch (MessagingException e) {
            assertThat(true, is(false));
        }
    }

    private Properties getProperties(String host, String port) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host); // smtp.gmail.com
        props.put("mail.smtp.port", port); // 587, 465 for ssl
        return props;
    }

    private Session getSession(String username, String password, Properties props) {
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

}