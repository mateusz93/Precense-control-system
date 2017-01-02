package neo.dmcs.controller;

import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.repository.*;
import neo.dmcs.service.GradeService;
import neo.dmcs.view.grade.StudentGradeDetailsView;
import neo.dmcs.view.grade.StudentGradeView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
@Controller
@Transactional
@RequestMapping("/grades")
public class GradeController {

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession session) {
        ModelAndView mvc = new ModelAndView("grade/studentGrades");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().equals(UserType.Student.name())) {
            prepareStudentGrades(user, mvc);
        } else {
            //gradeService.prepareTeacherPrecenseStatuses(courseId, mvc);
        }
        return mvc;
    }

    @RequestMapping(value = "/info/{courseId}", method = RequestMethod.GET)
    public ModelAndView gradesInfo(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("grade/studentGradesDetails");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().equals(UserType.Student.name())) {
            prepareStudentGradesDetails(courseId, mvc);
        } else {
            //gradeService.prepareTeacherPrecenseStatuses(courseId, mvc);
        }

        return mvc;
    }

    private void prepareStudentGrades(User user, ModelAndView mvc) {
        List<StudentGradeView> StudentGradeViews = new ArrayList<>();
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(user);
        for (StudentCourse studentCourse : studentCourses) {
            StudentGradeView studentGradeView = new StudentGradeView();
            studentGradeView.setCourseId(studentCourse.getTeacherCourse().getSubject().getId());
            studentGradeView.setName(studentCourse.getTeacherCourse().getSubject().getName());
            studentGradeView.setTeacherName(studentCourse.getTeacherCourse().getTeacher().getFullName());

            StudentGradeViews.add(studentGradeView);
        }
        mvc.addObject("gradesList", StudentGradeViews);
    }

    private void prepareStudentGradesDetails(int courseId, ModelAndView mvc) {
        List<StudentGradeDetailsView> StudentGradeDetailsViews = new ArrayList<>();
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        List<Grade> grades = gradeRepository.findByTeacherCourse(teacherCourse);
        for (Grade grade : grades) {
            StudentGradeDetailsView studentGradeDetailsView = new StudentGradeDetailsView();
            studentGradeDetailsView.setFinalGrade(grade.isFinalGrade());
            studentGradeDetailsView.setTime(grade.getTime().toString());
            studentGradeDetailsView.setValue(grade.getValue());
            studentGradeDetailsView.setPreviousGrade(grade.getPreviousGrade());

            StudentGradeDetailsViews.add(studentGradeDetailsView);
        }
        mvc.addObject("gradesDetailsList", StudentGradeDetailsViews);
    }
}
