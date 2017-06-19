package neo.dmcs.bpm.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.bpm.Const;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.ValidationException;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.RegisterView;
import org.activiti.engine.delegate.BpmnError;
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
public class ValidationServiceTask implements JavaDelegate {

    private final MessageSource messageSource;
    private final RegisterService registerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Validation user data");
        RegisterView form = (RegisterView) execution.getVariable("form");
        Locale locale = (Locale) execution.getVariable("locale");
        try {
            registerService.validate(form);
        } catch (ValidationException e) {
            mapFields(execution, form);
            setValidationMessages(execution, locale, e);
            throw new BpmnError(Const.VALIDATION_ERROR_CODE);
        }
    }

    private void setValidationMessages(DelegateExecution execution, Locale locale, ValidationException e) {
        if (e instanceof FieldEmptyException) {
            String fieldName = messageSource.getMessage(((FieldEmptyException) e).getFieldName(), null, locale);
            String message = messageSource.getMessage(e.getMessage(), null, locale);
            message = message.replace("{}", "'" + fieldName + "'");
            execution.setVariable("message", message);
            execution.setVariable("messageType", MessageType.DANGER.name());
            execution.setVariable("viewName","security/register");
        }
        execution.setVariable("message", messageSource.getMessage(e.getMessage(), null, locale));
        execution.setVariable("messageType", MessageType.DANGER.name());
        execution.setVariable("viewName", "security/register");
    }

    private void mapFields(DelegateExecution execution, RegisterView form) {
        execution.setVariable("newEmail", form.getEmail());
        execution.setVariable("firstName", form.getFirstName());
        execution.setVariable("lastName", form.getLastName());
        execution.setVariable("password", form.getPassword());
        execution.setVariable("confirmPassword", form.getConfirmPassword());
        execution.setVariable("type", form.getType());
    }

}
