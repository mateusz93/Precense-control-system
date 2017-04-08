package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.TeacherCourseRepository;
import neo.dmcs.view.course.SaveView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Mateusz Wieczorek, 14.05.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SaveService {

    private final TeacherCourseRepository teacherCourseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final MessageSource messageSource;

    private List<SaveView> getSubjects(User user) {
        List<Object[]> objects = null;//customRepository.findTeacherCourses();
        return getCastedResult(objects, user);
    }

    private List<SaveView> getCastedResult(List<Object[]> objects, User user) {
        List<SaveView> resultList = new ArrayList<>();
        for (Object[] object : objects) {
            SaveView saveView = getSaveView(object);
            TeacherCourse teacherCourse = teacherCourseRepository.findOne(saveView.getId());
            StudentCourse studentCourses = studentCourseRepository.findByStudentAndTeacherCourse(user, teacherCourse);
            if (studentCourses == null) {
                resultList.add(saveView);
            }
        }
        return resultList;
    }

    private SaveView getSaveView(Object[] object) {
        SaveView saveView = new SaveView();
        saveView.setId((Integer) object[0]);
        saveView.setSubjectName(String.valueOf(object[1]));
        saveView.setCoursesQuantity((Integer) object[4]);
        saveView.setTeacherName(String.valueOf(object[5]));
        saveView.setDescription(String.valueOf(object[6]));
        return saveView;
    }

    public ModelAndView prepareView(ModelAndView mvc, User user) {
        List<SaveView> subjectList = getSubjects(user);
        mvc.addObject("subjectList", subjectList);
        return mvc;
    }

    public ModelAndView prepareViewByCourseId(int courseId, ModelAndView mvc, User user, Locale locale) {
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(user);
        studentCourse.setTeacherCourse(teacherCourse);
        studentCourseRepository.save(studentCourse);
        mvc = prepareView(mvc, user);
        mvc.addObject("message", messageSource.getMessage("course.subscribed", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }
}
