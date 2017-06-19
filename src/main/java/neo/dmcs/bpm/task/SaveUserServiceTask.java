package neo.dmcs.bpm.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.RegisterView;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Wieczorek on 12.06.2017.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SaveUserServiceTask implements JavaDelegate {

    private final UserRepository userRepository;
    private final RegisterService registerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Saving user in database with active = false flag");
        RegisterView form = (RegisterView) execution.getVariable("form");
        String username = registerService.generateUsername(form.getFirstName(), form.getLastName());
        User user = registerService.createUser(form, username);
        user = userRepository.save(user);
        execution.setVariable("user", user);
    }
}
