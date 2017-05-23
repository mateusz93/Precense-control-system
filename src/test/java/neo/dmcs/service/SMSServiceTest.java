package neo.dmcs.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Mateusz Wieczorek on 9/28/16.
 */
public class SMSServiceTest {

    @Ignore
    @Test
    public void sendSMS() throws Exception {
        final String ACCOUNT_SID = "AC3d8d5a6178f996ab92bdc4d2d5931e44";
        final String AUTH_TOKEN = "d181f81cfc1f9fa21efb427aebe1b67d";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        PhoneNumber from = new PhoneNumber("+48732230590");
        PhoneNumber to = new PhoneNumber("+48721127200");
        String content = "Co jest z tobą";

        Message message = Message.creator(to, from, content).create();

        if (message.getStatus().equals(Message.Status.QUEUED)) {
            assertThat(true, is(true));
        } else if (message.getStatus().equals(Message.Status.FAILED)) {
            assertThat(true, is(false));
        }
    }

}