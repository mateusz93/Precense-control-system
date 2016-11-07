package neo.dmcs.service;

import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.TeacherCourseRepository;
import neo.dmcs.view.course.StudentCourseView;
import neo.dmcs.view.course.TeacherCourseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    public List<TeacherCourseView> getTeacherCoursesList(User user) {
        List<TeacherCourse> courses = teacherCourseRepository.findByTeacher(user);
        List<TeacherCourseView> courseViews = mapTeacherCoruses(courses, user);
        return courseViews;
    }

    public List<StudentCourseView> getStudentCoursesList(User user) {
        List<StudentCourse> courses = studentCourseRepository.findByStudent(user);
        List<StudentCourseView> courseViews = mapStudentCoruses(courses, user);
        return courseViews;
    }

    private List<TeacherCourseView> mapTeacherCoruses(List<TeacherCourse> courses, User user) {
        List<TeacherCourseView> courseViews = new ArrayList<>();
        for (TeacherCourse c : courses) {
            TeacherCourseView courseView = new TeacherCourseView();
            courseView.setCoursesQuantity(c.getSubject().getQuantity());
            courseView.setID(c.getId());
            courseView.setSubjectName(c.getSubject().getName());
        }
        return courseViews;
    }

    private List<StudentCourseView> mapStudentCoruses(List<StudentCourse> courses, User user) {
        List<StudentCourseView> courseViews = new ArrayList<>();
        for (StudentCourse c : courses) {
            StudentCourseView courseView = new StudentCourseView();
            courseView.setCoursesQuantity(c.getTeacherCourse().getSubject().getQuantity());
            courseView.setMinCoursesQuantity(c.getTeacherCourse().getSubject().getMinQuantity());
            courseView.setDescription(c.getTeacherCourse().getSubject().getDescription());
            courseView.setName(c.getTeacherCourse().getSubject().getName());
            courseView.setSubjectID(c.getTeacherCourse().getSubject().getId());
            TeacherCourse teacherCourse = teacherCourseRepository.findBySubjectAndStudentGroup(c.getTeacherCourse().getSubject(), user.getGroup());
            courseView.setTeacherName(teacherCourse.getTeacher().getFirstName() + " " +
                    teacherCourse.getTeacher().getLastName());
        }
        return courseViews;
    }


}
