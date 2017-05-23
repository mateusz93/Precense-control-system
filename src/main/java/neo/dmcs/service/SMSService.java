package neo.dmcs.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.repository.AppPropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SMSService {

    private final AppPropertyRepository appPropertyRepository;

    public void sendSMS(String recipient, String content) {
        log.info("Send sms invoked.");

        String ACCOUNT_SID = appPropertyRepository.findByName("sms.account_sid").getValue(); // AC3d8d5a6178f996ab92bdc4d2d5931e44
        String AUTH_TOKEN = appPropertyRepository.findByName("sms.auth_token").getValue(); // d181f81cfc1f9fa21efb427aebe1b67d

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String phoneNumber = appPropertyRepository.findByName("sms.from.number").getValue();
        PhoneNumber from = new PhoneNumber(phoneNumber);
        PhoneNumber to = new PhoneNumber(recipient);

        Message message = Message.creator(ACCOUNT_SID, to, from, content).create();

        if (message.getStatus().equals(Message.Status.FAILED)) {
            log.info("Sending sms failed. " + message.getErrorMessage());
        } else {
            log.info("Sent sms to number: " + message.getTo() + " with content: " + message.getBody());
        }
    }
}
