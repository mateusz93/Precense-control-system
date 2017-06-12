package neo.dmcs.bpm.task;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Wieczorek on 10.06.2017.
 */
@Slf4j
@Service
public class DeleteUserServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Delete incorrect user data from database");
        //TODO to implement
    }
}
