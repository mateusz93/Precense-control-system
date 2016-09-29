package neo.dmcs.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import neo.dmcs.dao.AppPropertyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */

public class SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Autowired
    private AppPropertyDao appPropertyDao;

    public void sendSMS(String recipient, String content) {

        String ACCOUNT_SID = appPropertyDao.findByName("sms.account_sid").getValue(); // AC3d8d5a6178f996ab92bdc4d2d5931e44
        String AUTH_TOKEN = appPropertyDao.findByName("sms.auth_token").getValue(); // d181f81cfc1f9fa21efb427aebe1b67d

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String phoneNumber = appPropertyDao.findByName("sms.from.number").getValue();
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
