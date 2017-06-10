package neo.dmcs.bpm.task;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @author Mateusz Wieczorek on 31.05.2017.
 */
@Slf4j
public class ValidationServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Validation user data");
        //TODO
    }
}
