package neo.dmcs.bpm.message;

import lombok.extern.slf4j.Slf4j;
import neo.dmcs.bpm.Message;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Wieczorek on 18.06.2017.
 */
@Slf4j
@Service
public class ValidationMessage implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Sending validation message");
        String messageValue = (String) execution.getVariable("message");
        String messageType = (String) execution.getVariable("messageType");
        Message message = Message.builder()
                .value(messageValue)
                .type(messageType)
                .build();
        execution.setVariable("message", message);
    }
}
