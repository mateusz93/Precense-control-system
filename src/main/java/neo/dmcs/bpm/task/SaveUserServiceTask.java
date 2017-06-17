package neo.dmcs.bpm.task;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Wieczorek on 12.06.2017.
 */
@Slf4j
@Service
public class SaveUserServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Saving user in database with active = false flag");
    }
}
