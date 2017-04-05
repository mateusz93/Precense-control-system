package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.repository.AppPropertyRepository;
import neo.dmcs.repository.EmailTemplateRepository;
import neo.dmcs.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author by Mateusz Wieczorek on 9/27/16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class EmailService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final AppPropertyRepository appPropertyRepository;

    public void sendActivationLink(User user, String activationToken) {
        String recipient = user.getEmail();
        String subject = "Link aktywacyjny";
        String content = "Poniżej znajduje sie link aktywacyjny wazny 24h.\n\n";
        content += activationToken + "\n\n";
        sendEmail(recipient, subject, content);
    }

    public void sendEmail(String recipient, String subject, String content) {
        String from = appPropertyRepository.findByName("email.from.adress").getValue();
        String username = appPropertyRepository.findByName("email.from.username").getValue();
        String password = appPropertyRepository.findByName("email.from.password").getValue();
        String port = appPropertyRepository.findByName("email.port").getValue();
        String host = appPropertyRepository.findByName("email.host").getValue();
        String auth = appPropertyRepository.findByName("email.smtp.auth").getValue();
        String isStarttls = appPropertyRepository.findByName("email.smtp.starttls.enable").getValue();

        Properties props = getProperties(host, port, auth, isStarttls);
        Session session = getSession(username, password, props);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            log.info("Sent message successfully to " + recipient);
        } catch (MessagingException e) {
            log.error("Message not sent");
            throw new RuntimeException(e);
        }
    }

    private Properties getProperties(String host, String port, String auth, String isStarttls) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", isStarttls);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
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
