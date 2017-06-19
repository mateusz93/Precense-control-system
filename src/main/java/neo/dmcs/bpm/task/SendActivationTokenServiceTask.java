package neo.dmcs.bpm.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.model.User;
import neo.dmcs.service.EmailService;
import neo.dmcs.service.RegisterService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Mateusz Wieczorek on 31.05.2017.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SendActivationTokenServiceTask implements JavaDelegate {

    private final RegisterService registerService;
    private final EmailService emailService;
    private final MessageSource messageSource;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Generate and send activation token");
        User user = (User) execution.getVariable("user");
        String token = registerService.generateToken(user);
        String activationLink = registerService.generateActivationLink(token, execution.getProcessInstanceId());
        emailService.sendActivationLink(user, activationLink);
        Locale locale = (Locale) execution.getVariable("locale");
        execution.setVariable("message", messageSource.getMessage("register.userCreated", null, locale));
        execution.setVariable("messageType", MessageType.SUCCESS.name());
    }
}
