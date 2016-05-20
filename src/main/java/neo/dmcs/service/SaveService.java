package neo.dmcs.service;

import neo.dmcs.dao.CustomDao;
import neo.dmcs.dao.StudentCourseDao;
import neo.dmcs.dao.TeacherCourseDao;
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
@Service("saveService")
public class SaveService {

    private static final Logger logger = LoggerFactory.getLogger(SaveService.class);

    @Autowired
    private CustomDao customDao;

    @Autowired
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

    public List<SaveView> getSubjects(User user) {
        List<Object[]> objects = customDao.findTeacherCourses();
        return getCastedResult(objects, user);
    }

    private List<SaveView> getCastedResult(List<Object[]> objects, User user) {
        List<SaveView> resultList = new ArrayList<SaveView>();
        for (Object[] object : objects) {
            SaveView saveView = new SaveView();
            saveView.setId((Integer) object[0]);
            saveView.setSubjectName(String.valueOf(object[1]));
            saveView.setDepartmentName(String.valueOf(object[2]));
            saveView.setType(String.valueOf(object[3]));
            saveView.setCoursesQuantity((Integer) object[4]);
            saveView.setTeacherName(String.valueOf(object[5]));
            saveView.setDescription(String.valueOf(object[6]));

            TeacherCourse teacherCourse = teacherCourseDao.findById(saveView.getId());
            try {
                StudentCourse studentCourses = studentCourseDao.findByStudentAndTeacherCourse(user, teacherCourse);
            } catch (NoResultException e) {
                resultList.add(saveView);
            }
        }
        return resultList;
    }
}
