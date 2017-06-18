package neo.dmcs.bpm;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.ValidationException;
import neo.dmcs.model.Token;
import neo.dmcs.repository.TokenRepository;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.RegisterView;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static neo.dmcs.bpm.Const.CONFIRMATION_TOKEN_Message;

/**
 * @author Mateusz Wieczorek on 10.06.2017.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BpmProcessService {

    private static final String MVC_DEFAULT = "security/register";

    private final HistoryService historyService;
    private final RuntimeService runtimeService;
    private final MessageSource messageSource;
    private final TokenRepository tokenRepository;
    private final RegisterService registerService;

    public ModelAndView startProcess(RegisterView form, Locale locale) {
        Map<String, Object> variables = Maps.newTreeMap();
        variables.put("form", form);
        variables.put("locale", locale);
        log.info("Starting new process with variables: " + variables);
        ProcessInstance process = runtimeService.startProcessInstanceByMessage(Const.PROCESS_STARTED, variables);
        return getMVC(process);
    }

    private ModelAndView getMVC(ProcessInstance process) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        Map<String, Object> variables;
        try {
            variables = runtimeService.getVariables(process.getId());
            if (variables.get("message") != null) {
                mvc.addObject("message", variables.get("message"));
                mvc.addObject("messageType", variables.get("messageType"));
            }
            mvc.addObject("newEmail", variables.get("newEmail"));
            mvc.addObject("firstName", variables.get("firstName"));
            mvc.addObject("lastName", variables.get("lastName"));
            mvc.addObject("password", variables.get("password"));
            mvc.addObject("confirmPassword", variables.get("confirmPassword"));
            mvc.addObject("type", variables.get("type"));
        } catch (ActivitiObjectNotFoundException e) {
            RegisterView view = getViewVariables(process);
            Message message = getMessage(process);
            mvc.addObject("message", message.getValue());
            mvc.addObject("messageType", message.getType());
            mvc.addObject("newEmail", view.getEmail());
            mvc.addObject("firstName", view.getFirstName());
            mvc.addObject("lastName", view.getLastName());
            mvc.addObject("password", view.getPassword());
            mvc.addObject("confirmPassword", view.getConfirmPassword());
            mvc.addObject("type", view.getType());
        }

        return mvc;
    }

    private Message getMessage(ProcessInstance process) {
        return (Message) historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(process.getProcessInstanceId())
                .list()
                .stream()
                .filter(p -> p.getValue() instanceof Message)
                .map(HistoricVariableInstance::getValue)
                .collect(Collectors.toList())
                .get(0);
    }

    private RegisterView getViewVariables(ProcessInstance process) {
        return (RegisterView) historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(process.getProcessInstanceId())
                .list()
                .stream()
                .filter(p -> p.getValue() instanceof RegisterView)
                .map(HistoricVariableInstance::getValue)
                .collect(Collectors.toList())
                .get(0);
    }

    private List<Object> getVariables(ProcessInstance process) {
        return historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(process.getProcessInstanceId())
                .list()
                .stream()
                .map(HistoricVariableInstance::getValue)
                .collect(Collectors.toList());
    }

    public ModelAndView confirmRegistration(Locale locale, String tokenAsString, String processInstanceId) {
        ModelAndView mvc = new ModelAndView("redirect:/login");
        try {
            Token token = tokenRepository.findByToken(tokenAsString);
            registerService.validateToken(token);
        } catch (ValidationException e) {
            mvc.addObject("message", messageSource.getMessage(e.getMessage(), null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        }
        Execution execution = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName(CONFIRMATION_TOKEN_Message)
                .processInstanceId(processInstanceId)
                .singleResult();
        if (execution == null) {
            mvc.addObject("message", messageSource.getMessage("register.token.expiry", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        }
        runtimeService.messageEventReceived(CONFIRMATION_TOKEN_Message, execution.getId());

        mvc.addObject("message", messageSource.getMessage("view.register.confirmed", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        mvc.setViewName("security/register");
        return mvc;
    }
}
