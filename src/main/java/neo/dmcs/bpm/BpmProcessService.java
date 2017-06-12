package neo.dmcs.bpm;

import lombok.RequiredArgsConstructor;
import neo.dmcs.bpm.signal.Const;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.RegisterView;
import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * @author Mateusz Wieczorek on 10.06.2017.
 */
@RequiredArgsConstructor
@Service
public class BpmProcessService {

    private static final String MVC_DEFAULT = "security/register";

    private final UserRepository userRepository;
    private final RegisterService registerService;
    private final RuntimeService runtimeService;

    public ModelAndView startProcess(RegisterView form, Locale locale) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        runtimeService.startProcessInstanceById(Const.PROCESS_ID);
        return mvc;
    }
}
