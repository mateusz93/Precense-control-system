package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.model.Field;
import neo.dmcs.model.Subject;
import neo.dmcs.repository.FieldRepository;
import neo.dmcs.repository.SubjectRepository;
import neo.dmcs.view.course.SubjectView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;

/**
 * @author Mateusz Wieczorek on 06.04.2017.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SubjectService {

    private final FieldRepository fieldRepository;
    private final MessageSource messageSource;
    private final SubjectRepository subjectRepository;

    public ModelAndView prepareView(ModelAndView mvc) {
        List<Subject> subjectList = subjectRepository.findAll();
        mvc.setViewName("subject/subjectList");
        mvc.addObject("subjectList", subjectList);
        return mvc;
    }

    public ModelAndView prepareAddNewSubjectView(ModelAndView mvc) {
        List<Field> fields = fieldRepository.findAll();
        mvc.addObject("fieldList", fields);
        return mvc;
    }

    public ModelAndView addNewSubject(SubjectView subjectView, ModelAndView mvc, Locale locale) {
        Field field = fieldRepository.findByName(subjectView.getField());
        Subject subject = mapSubject(subjectView, field);
        subjectRepository.save(subject);
        List<Field> fields = fieldRepository.findAll();
        mvc.addObject("fieldList", fields);
        mvc.addObject("message", messageSource.getMessage("subject.added", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    private Subject mapSubject(SubjectView subjectView, Field field) {
        Subject subject = new Subject();
        subject.setName(subjectView.getName());
        subject.setDescription(subjectView.getDescription());
        subject.setField(field);
        subject.setMinQuantity(subjectView.getMinQuantity());
        subject.setQuantity(subjectView.getQuantity());
        subject.setYearOfStudy(subjectView.getYearOfStudy());
        return subject;
    }

    public ModelAndView deleteSubjectById(String subjectId, ModelAndView mvc, Locale locale) {
        Subject subject = subjectRepository.findOne(Integer.valueOf(subjectId));
        subjectRepository.delete(subject);
        mvc.addObject("message", messageSource.getMessage("subject.deleted", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return prepareView(mvc);
    }
}
