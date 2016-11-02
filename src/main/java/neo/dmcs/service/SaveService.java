package neo.dmcs.service;

import neo.dmcs.repository.CustomRepository;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.TeacherCourseRepository;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.view.course.SaveView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
@Service
public class SaveService {

    private static final Logger logger = LoggerFactory.getLogger(SaveService.class);

    @Autowired
    private CustomRepository customRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<SaveView> getSubjects(User user) {
        List<Object[]> objects = customRepository.findTeacherCourses();
        return getCastedResult(objects, user);
    }

    private List<SaveView> getCastedResult(List<Object[]> objects, User user) {
        List<SaveView> resultList = new ArrayList<>();
        for (Object[] object : objects) {
            SaveView saveView = new SaveView();
            saveView.setId((Integer) object[0]);
            saveView.setSubjectName(String.valueOf(object[1]));
            saveView.setDepartmentName(String.valueOf(object[2]));
            saveView.setType(String.valueOf(object[3]));
            saveView.setCoursesQuantity((Integer) object[4]);
            saveView.setTeacherName(String.valueOf(object[5]));
            saveView.setDescription(String.valueOf(object[6]));

            TeacherCourse teacherCourse = teacherCourseRepository.findOne(saveView.getId());
            try {
                StudentCourse studentCourses = studentCourseRepository.findByStudentAndSubject(user, teacherCourse.getSubject());
            } catch (NoResultException e) {
                resultList.add(saveView);
            }
        }
        return resultList;
    }
}
