package neo.dmcs.service;

import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.Subject;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.TeacherCourseRepository;
import neo.dmcs.view.precense.StudentPrecensesView;
import neo.dmcs.view.precense.TeacherPrecensesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
@Service
public class PrecenseService {

    private static final Logger logger = LoggerFactory.getLogger(PrecenseService.class);

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    public List<StudentPrecensesView> getStudentPrecenses(User user) {
        List<StudentPrecensesView> studentPrecensesViews = new ArrayList<>();
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(user);
        for (StudentCourse studentCourse : studentCourses) {
            Subject subject = studentCourse.getTeacherCourse().getSubject();
            TeacherCourse teacherCourse = teacherCourseRepository.findBySubjectAndStudentGroup(subject, user.getGroup());
            StudentPrecensesView studentPrecensesView = new StudentPrecensesView();
            studentPrecensesView.setSubjectName(subject.getName());
            studentPrecensesView.setTeacherName(teacherCourse.getTeacher().getFirstName() + " " + teacherCourse.getTeacher().getLastName());
            studentPrecensesView.setCourseId(teacherCourse.getId());
            studentPrecensesView.setQuantity(subject.getQuantity());
            studentPrecensesViews.add(studentPrecensesView);
        }
        return studentPrecensesViews;
    }

    public List<TeacherPrecensesView> getTeacherPrecenses(User user) {

        return null;
    }
}
