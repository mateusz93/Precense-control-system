package neo.dmcs.bpm.message;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Wieczorek on 31.05.2017.
 */
@Slf4j
@Service
public class SendActivationTokenMessage implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Generate and send activation token");
        //TODO to implement
    }
}
