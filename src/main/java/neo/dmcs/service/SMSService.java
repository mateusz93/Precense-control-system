package neo.dmcs.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import neo.dmcs.repository.AppPropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */
@Service
public class SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Autowired
    private AppPropertyRepository appPropertyRepository;

    public void sendSMS(String recipient, String content) {
        logger.info("Send sms invoked.");

        String ACCOUNT_SID = appPropertyRepository.findByName("sms.account_sid").getValue(); // AC3d8d5a6178f996ab92bdc4d2d5931e44
        String AUTH_TOKEN = appPropertyRepository.findByName("sms.auth_token").getValue(); // d181f81cfc1f9fa21efb427aebe1b67d

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String phoneNumber = appPropertyRepository.findByName("sms.from.number").getValue();
        PhoneNumber from = new PhoneNumber(phoneNumber);
        PhoneNumber to = new PhoneNumber(recipient);

        Message message = Message.create(to, from, content).execute();

        if (message.getStatus().equals(Message.Status.FAILED)) {
            logger.info("Sending sms failed. " + message.getErrorMessage());
        } else {
            logger.info("Sent sms to number: " + message.getTo() + " with content: " + message.getBody());
        }
    }
}
