package neo.dmcs.service;

import neo.dmcs.repository.CustomRepository;
import neo.dmcs.model.User;
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
    private CustomRepository customRepository;

    public List<StudentCourseView> getStudentCoursesList(User user) {
        List<Object[]> objects = customRepository.findStudentCoursesByUserId(user.getId());
        return getStudentCastedResult(objects);
    }

    public List<TeacherCourseView> getTeacherCoursesList(User user) {
        List<Object[]> objects = customRepository.findTeacherCoursesByUserId(user.getId());
        return getTeacherCastedResult(objects);
    }

    private List<TeacherCourseView> getTeacherCastedResult(List<Object[]> objects) {
        List<TeacherCourseView> resultList = new ArrayList<TeacherCourseView>();
        for (Object[] object : objects) {
            TeacherCourseView tpv = new TeacherCourseView();
            tpv.setID((Integer) object[0]);
            tpv.setSubjectName(String.valueOf(object[1]));
            tpv.setDepartmentName(String.valueOf(object[2]));
            tpv.setType(String.valueOf(object[3]));
            tpv.setCoursesQuantity((Integer) object[4]);
            resultList.add(tpv);
        }
        return resultList;
    }

    private List<StudentCourseView> getStudentCastedResult(List<Object[]> objects) {
        List<StudentCourseView> resultList = new ArrayList<StudentCourseView>();
        for (Object[] object : objects) {
            StudentCourseView spv = new StudentCourseView();
            spv.setSubjectName(String.valueOf(object[0]));
            spv.setDepartmentName(String.valueOf(object[1]));
            spv.setType(String.valueOf(object[2]));
            spv.setCourseID((Integer) object[3]);
            spv.setCoursesQuantity((Integer) object[4]);
            spv.setTeacherName(String.valueOf(object[5]));
            resultList.add(spv);
        }
        return resultList;
    }


}
