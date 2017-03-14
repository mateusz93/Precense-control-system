package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.TeacherCourseRepository;
import neo.dmcs.view.course.SaveView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SaveService {

    private final TeacherCourseRepository teacherCourseRepository;
    private final StudentCourseRepository studentCourseRepository;

    public List<SaveView> getSubjects(User user) {
        List<Object[]> objects = null;//customRepository.findTeacherCourses();
        return getCastedResult(objects, user);
    }

    private List<SaveView> getCastedResult(List<Object[]> objects, User user) {
        List<SaveView> resultList = new ArrayList<>();
        for (Object[] object : objects) {
            SaveView saveView = new SaveView();
            saveView.setId((Integer) object[0]);
            saveView.setSubjectName(String.valueOf(object[1]));
            saveView.setCoursesQuantity((Integer) object[4]);
            saveView.setTeacherName(String.valueOf(object[5]));
            saveView.setDescription(String.valueOf(object[6]));

            TeacherCourse teacherCourse = teacherCourseRepository.findOne(saveView.getId());
            try {
                StudentCourse studentCourses = studentCourseRepository.findByStudentAndTeacherCourse(user, teacherCourse);
            } catch (NoResultException e) {
                resultList.add(saveView);
            }
        }
        return resultList;
    }
}
