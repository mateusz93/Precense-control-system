package neo.dmcs.bpm.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserStatus;
import neo.dmcs.model.Token;
import neo.dmcs.model.User;
import neo.dmcs.repository.TokenRepository;
import neo.dmcs.repository.UserRepository;
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
public class ActivateUserAccountServiceTask implements JavaDelegate {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final MessageSource messageSource;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Activation user account");
        Locale locale = (Locale) execution.getVariable("locale");
        User user = (User) execution.getVariable("user");
        user.setStatus(UserStatus.ACTIVE.name());
        userRepository.save(user);
        execution.setVariable("message", messageSource.getMessage("view.register.confirmed", null, locale));
        execution.setVariable("messageType", MessageType.SUCCESS.name());
        Token token = tokenRepository.findByUser(user);
        tokenRepository.delete(token);
    }
}
